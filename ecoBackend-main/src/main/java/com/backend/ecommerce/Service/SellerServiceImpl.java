package com.backend.ecommerce.Service;


import com.backend.ecommerce.Exception.BadRequestException;
import com.backend.ecommerce.Payload.DTO.SellerRequestDto;
import com.backend.ecommerce.MapStruct.SellerMapper;

import com.backend.ecommerce.Model.Seller;
import com.backend.ecommerce.Model.User;
import com.backend.ecommerce.Model.UserRole;
import com.backend.ecommerce.Payload.DTO.SellerDTO;
import com.backend.ecommerce.Repository.SellerRepository;
import com.backend.ecommerce.Repository.UserRepository;
import com.backend.ecommerce.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // Assuming Spring Security for password hashing
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository; // To manage User entity
    private final SellerMapper sellerMapper;
    private final PasswordEncoder passwordEncoder; // For hashing passwords


    // Register a new seller (with new user)
    @Transactional
    @Override
    public SellerDTO registerNewSeller(SellerRequestDto sellerRequestDto) throws BadRequestException {
        // 1. Validate if user/seller already exists with the given email/GST
        if (userRepository.existsByEmail(sellerRequestDto.getEmail())) {
            throw new BadRequestException("User with this email already exists.");
        }
        if (sellerRepository.existsByGstNumber(sellerRequestDto.getGstNumber())) {
            throw new BadRequestException("Seller with this GST Number already exists.");
        }
        if (sellerRepository.existsByEmail(sellerRequestDto.getEmail())) {
            throw new BadRequestException("Seller with this email already registered.");
        }

 String rawPassword = UUID.randomUUID().toString().substring(0,8);

        String encodePassword = passwordEncoder.encode(rawPassword);
        // 2. Create a new User
        User newUser = new User();
        newUser.setEmail(sellerRequestDto.getEmail());
        // User model has fields with capitalized names for Password and email — use setters
        newUser.setPassword(passwordEncoder.encode(sellerRequestDto.getPassword())); // Hash password
        newUser.setRoles(UserRole.ROLE_SELLER);
        User savedUser = userRepository.save(newUser);

        // 3. Create Seller entity and link to User
        Seller seller = sellerMapper.toEntity(sellerRequestDto);
        seller.setUser(savedUser); // Link the saved User
        seller.setCreatedAt(LocalDateTime.now()); // Set timestamp (though @CreationTimestamp handles this)
        // Ensure email on Seller is also set if it's a direct field
        seller.setEmail(sellerRequestDto.getEmail());
        // randomPassword is likely for an initial auto-generated password,
        // which would be handled during user creation/reset. Ignoring for now.
        seller.setRandomPassword(encodePassword); // Or generate if needed for specific flow

        Seller savedSeller = sellerRepository.save(seller);
        return sellerMapper.toDto(savedSeller);
    }

    // Create a seller for an existing user (less common, usually registerNewSeller is used)
    @Transactional
    @Override
    public SellerDTO createSellerForExistingUser(Long userId, SellerRequestDto sellerRequestDto) throws BadRequestException, Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with id: " + userId));

        if (sellerRepository.findByUserId(userId).isPresent()) {
            throw new BadRequestException("A seller already exists for this user.");
        }
        if (sellerRepository.existsByGstNumber(sellerRequestDto.getGstNumber())) {
            throw new BadRequestException("Seller with this GST Number already exists.");
        }
        if (sellerRepository.existsByEmail(sellerRequestDto.getEmail())) {
            throw new BadRequestException("Seller with this email already registered.");
        }

        Seller seller = sellerMapper.toEntity(sellerRequestDto);
        seller.setUser(user);
        seller.setEmail(sellerRequestDto.getEmail());
        seller.setCreatedAt(LocalDateTime.now());

        Seller savedSeller = sellerRepository.save(seller);
        return sellerMapper.toDto(savedSeller);
    }


    @Override
    public SellerDTO getSellerById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + id));
        return sellerMapper.toDto(seller);
    }

    @Override
    public SellerDTO getSellerByUserId(Long userId) {
        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found for user id: " + userId));
        return sellerMapper.toDto(seller);
    }

    @Override
    public List<SellerDTO> getAllSellers() {
        List<SellerDTO> list = sellerRepository.findAll().stream()
                .map(sellerMapper::toDto)
                .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    @Transactional
    @Override
    public SellerDTO updateSeller(Long id, SellerRequestDto sellerRequestDto) throws BadRequestException {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + id));

        // Basic validation for unique fields if they are being updated
        if (sellerRequestDto.getGstNumber() != null && !existingSeller.getGstNumber().equals(sellerRequestDto.getGstNumber())) {
            if (sellerRepository.existsByGstNumber(sellerRequestDto.getGstNumber())) {
                throw new BadRequestException("GST Number already registered by another seller.");
            }
        }
        if (sellerRequestDto.getEmail() != null && !existingSeller.getEmail().equals(sellerRequestDto.getEmail())) {
            if (userRepository.existsByEmail(sellerRequestDto.getEmail())) { // Check user email table
                throw new BadRequestException("User with this email already exists.");
            }
            if (sellerRepository.existsByEmail(sellerRequestDto.getEmail())) { // Check seller email table
                throw new BadRequestException("Seller with this email already registered.");
            }
            // Also update the User's email if the seller's email is linked and updated
            User user = existingSeller.getUser();
            if (user != null) {
                user.setEmail(sellerRequestDto.getEmail());
                userRepository.save(user);
            }
        }

        sellerMapper.updateSellerFromDto(sellerRequestDto, existingSeller);
        // Explicitly set email if it's updated in DTO and not handled by mapper update
        if (sellerRequestDto.getEmail() != null) {
            existingSeller.setEmail(sellerRequestDto.getEmail());
        }

        // If photoUrl needs a separate update or logic (e.g., file upload), handle it here
        if (sellerRequestDto.getPhotoUrl() != null) {
            existingSeller.setPhotoUrl(sellerRequestDto.getPhotoUrl());
        }

        Seller updatedSeller = sellerRepository.save(existingSeller);
        return sellerMapper.toDto(updatedSeller);
    }

    @Transactional
    @Override
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + id));

        // Before deleting seller, consider what happens to the associated User.
        // Option 1: Delete the User as well (if User is solely for this seller)
        // userRepository.delete(seller.getUser());
        // Option 2: Disassociate the User and keep it (if User might have other roles/data)
        // seller.setUser(null);
        // sellerRepository.save(seller); // Save the disassociated seller
        sellerRepository.delete(seller);
    }
}
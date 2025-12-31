package com.backend.ecommerce.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class JwtValidator extends OncePerRequestFilter {
   private final UserDetailsService userDetailsService;
   private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(JwtConstant.JWT_HEADER);
        String token = null;
        String username = null;

        try {
            // Check for Authorization header
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7); // Remove "Bearer "
                try {
                    username = jwtProvider.extractUsername(token);
                } catch (Exception e) {
                    System.out.println("Invalid JWT token: " + e.getMessage());
                    username = null;
                }
            }

            // Validate and authenticate user only if we have a valid token
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtProvider.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    System.out.println("Error loading user or validating token: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("JWT Filter Exception: " + e.getMessage());
        }

        // Continue filter chain
        filterChain.doFilter(request, response);

        // Debug logs
        System.out.println("JWT Token: " + token);
        System.out.println("JWT Username: " + username);
    }
}

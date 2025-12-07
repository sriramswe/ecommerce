package com.backend.ecommerce.Repository;

import com.backend.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> { User findByEmail(String Email);

    boolean existsByEmail(String email);
}

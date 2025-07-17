package com.rezumei.rezumei_backend.repository;

import com.rezumei.rezumei_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
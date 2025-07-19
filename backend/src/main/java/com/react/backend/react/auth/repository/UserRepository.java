package com.react.backend.react.auth.repository;

import com.react.backend.react.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUserId(String userId);
  boolean existsByEmail(String email);
}
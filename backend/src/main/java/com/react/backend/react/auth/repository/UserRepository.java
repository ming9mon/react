package com.react.backend.react.auth.repository;

import com.react.backend.shared.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TUser, Long> {
  boolean existsByUserId(String userId);
  boolean existsByEmail(String email);
  Optional<TUser> findByUserId(String userId);
}
package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<TLoginHistory, Long> {
}
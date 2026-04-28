package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TMultilingualBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultilingualBaseRepository extends JpaRepository<TMultilingualBase, String> {
}

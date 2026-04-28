package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TMultilingualValue;
import com.react.backend.shared.entity.TMultilingualValueId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultilingualValueRepository extends JpaRepository<TMultilingualValue, TMultilingualValueId> {
}

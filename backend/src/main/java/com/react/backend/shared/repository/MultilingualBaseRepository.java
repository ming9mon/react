package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TMultilingualBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MultilingualBaseRepository extends JpaRepository<TMultilingualBase, String> {

  @Query("SELECT m FROM TMultilingualBase m " +
      "WHERE (:multilingualKey IS NULL OR m.multilingualKey LIKE %:multilingualKey%) " +
      "AND (:multilingualType IS NULL OR m.multilingualType = :multilingualType) " +
      "AND (:useYn IS NULL OR m.useYn = :useYn) " +
      "ORDER BY m.createdAt DESC")
  Page<TMultilingualBase> findByCondition(
      @Param("multilingualKey") String multilingualKey,
      @Param("multilingualType") String multilingualType,
      @Param("useYn") String useYn,
      Pageable pageable
  );
}

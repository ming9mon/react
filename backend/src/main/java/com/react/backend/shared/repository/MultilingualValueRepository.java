package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TMultilingualValue;
import com.react.backend.shared.entity.TMultilingualValueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MultilingualValueRepository extends JpaRepository<TMultilingualValue, TMultilingualValueId> {

  @Query("SELECT v FROM TMultilingualValue v JOIN FETCH v.langCd WHERE v.id.multilingualKey = :multilingualKey")
  List<TMultilingualValue> findAllByMultilingualKey(@Param("multilingualKey") String multilingualKey);

  @Modifying
  @Query("DELETE FROM TMultilingualValue v WHERE v.id.multilingualKey = :multilingualKey")
  void deleteAllByMultilingualKey(@Param("multilingualKey") String multilingualKey);
}

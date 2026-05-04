package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TMultilingualValue;
import com.react.backend.shared.entity.TMultilingualValueId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MultilingualValueRepository extends JpaRepository<TMultilingualValue, TMultilingualValueId> {

  @Query("SELECT v FROM TMultilingualValue v WHERE v.multilingualKey.useYn = 'Y' AND v.id.langCd = :langCd")
  List<TMultilingualValue> findAllActiveByLangCd(@Param("langCd") String langCd);
}

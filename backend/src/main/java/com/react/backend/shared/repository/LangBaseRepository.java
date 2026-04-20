package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TLangBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangBaseRepository extends JpaRepository<TLangBase, String> {
}
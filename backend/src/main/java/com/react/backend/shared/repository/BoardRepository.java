package com.react.backend.shared.repository;

import com.react.backend.shared.entity.TBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<TBoard, Long> {

}
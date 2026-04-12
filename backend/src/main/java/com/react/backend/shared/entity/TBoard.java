package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_board")
public class TBoard extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq", nullable = false)
  private Long id;

  @NotNull
  @Lob
  @Column(name = "content", nullable = false)
  private String content;

  @Size(max = 100)
  @NotNull
  @Column(name = "title", nullable = false, length = 100)
  private String title;

}
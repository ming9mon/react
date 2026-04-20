package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_multilingual_base", uniqueConstraints = {
    @UniqueConstraint(name = "uk_t_multilingual_base_01", columnNames = {"multilingual_cd"})
})
public class TMultilingualBase {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_multilingual_base_id_gen")
  @SequenceGenerator(name = "t_multilingual_base_id_gen", sequenceName = "seq_multilingual", allocationSize = 1)
  @Column(name = "multilingual_seq", nullable = false)
  private Long id;

  @Size(max = 6)
  @NotNull
  @Column(name = "multilingual_cd", nullable = false, length = 6)
  private String multilingualCd;

  @Size(max = 20)
  @NotNull
  @Column(name = "multilingual_type_cd", nullable = false, length = 20)
  private String multilingualTypeCd;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @Size(max = 500)
  @Column(name = "multilingual_desc", length = 500)
  private String multilingualDesc;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @Column(name = "created_by", nullable = false)
  private Long createdBy;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @NotNull
  @Column(name = "updated_by", nullable = false)
  private Long updatedBy;

  @OneToMany
  private Set<TMultilingualValue> tMultilingualValues = new LinkedHashSet<>();

}
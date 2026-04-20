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
@Table(name = "t_lang_base")
public class TLangBase {
  @Id
  @Size(max = 10)
  @Column(name = "lang_cd", nullable = false, length = 10)
  private String langCd;

  @Size(max = 20)
  @NotNull
  @Column(name = "lang_nm", nullable = false, length = 20)
  private String langNm;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

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

  @OneToMany(mappedBy = "langCd")
  private Set<TCommonCode> tCommonCodes = new LinkedHashSet<>();

  @OneToMany(mappedBy = "langCd")
  private Set<TMultilingualValue> tMultilingualValues = new LinkedHashSet<>();

  @OneToMany(mappedBy = "langCd")
  private Set<TUser> tUsers = new LinkedHashSet<>();

}
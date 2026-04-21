package com.react.backend.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TMultilingualValueId implements java.io.Serializable {
  private static final long serialVersionUID = -3752036642427524746L;
  @Size(max = 6)
  @NotNull
  @Comment("다국어 키 (기본 테이블 FK)")
  @Column(name = "multilingual_key", nullable = false, length = 6)
  private String multilingualKey;

  @Size(max = 10)
  @NotNull
  @Comment("언어 코드")
  @Column(name = "lang_cd", nullable = false, length = 10)
  private String langCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TMultilingualValueId entity = (TMultilingualValueId) o;
    return Objects.equals(this.multilingualKey, entity.multilingualKey) &&
        Objects.equals(this.langCd, entity.langCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multilingualKey, langCd);
  }

}
package com.react.backend.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TMultilingualValueId implements java.io.Serializable {
  private static final long serialVersionUID = -98969238394857530L;
  @NotNull
  @Column(name = "multilingual_seq", nullable = false)
  private Long multilingualSeq;

  @Size(max = 10)
  @NotNull
  @Column(name = "lang_cd", nullable = false, length = 10)
  private String langCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TMultilingualValueId entity = (TMultilingualValueId) o;
    return Objects.equals(this.multilingualSeq, entity.multilingualSeq) &&
        Objects.equals(this.langCd, entity.langCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multilingualSeq, langCd);
  }

}
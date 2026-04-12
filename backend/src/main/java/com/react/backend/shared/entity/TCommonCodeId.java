package com.react.backend.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TCommonCodeId implements Serializable {
  private static final long serialVersionUID = 710281278319927893L;
  @Size(max = 50)
  @NotNull
  @Column(name = "GROUP_CD", nullable = false, length = 50)
  private String groupCd;

  @Size(max = 50)
  @NotNull
  @Column(name = "CODE", nullable = false, length = 50)
  private String code;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TCommonCodeId entity = (TCommonCodeId) o;
    return Objects.equals(this.code, entity.code) &&
        Objects.equals(this.groupCd, entity.groupCd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, groupCd);
  }

}
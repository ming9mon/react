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
public class TCommonCodeId implements java.io.Serializable {
  private static final long serialVersionUID = -7238845817074808796L;
  @Size(max = 50)
  @NotNull
  @Column(name = "group_code", nullable = false, length = 50)
  private String groupCode;

  @Size(max = 50)
  @NotNull
  @Column(name = "code_value", nullable = false, length = 50)
  private String codeValue;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TCommonCodeId entity = (TCommonCodeId) o;
    return Objects.equals(this.groupCode, entity.groupCode) &&
        Objects.equals(this.codeValue, entity.codeValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupCode, codeValue);
  }

}
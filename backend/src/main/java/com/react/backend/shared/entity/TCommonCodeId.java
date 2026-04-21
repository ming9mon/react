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
public class TCommonCodeId implements java.io.Serializable {
  private static final long serialVersionUID = 778232799167617987L;
  @Size(max = 255)
  @NotNull
  @Comment("코드 그룹 코드")
  @Column(name = "group_code", nullable = false)
  private String groupCode;

  @Size(max = 255)
  @NotNull
  @Comment("언어 코드")
  @Column(name = "lang_cd", nullable = false)
  private String langCd;

  @Size(max = 50)
  @NotNull
  @Comment("코드 값")
  @Column(name = "code_value", nullable = false, length = 50)
  private String codeValue;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TCommonCodeId entity = (TCommonCodeId) o;
    return Objects.equals(this.langCd, entity.langCd) &&
        Objects.equals(this.groupCode, entity.groupCode) &&
        Objects.equals(this.codeValue, entity.codeValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(langCd, groupCode, codeValue);
  }

}
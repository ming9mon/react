package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Comment("다국어 값 테이블")
@Entity
@Table(name = "t_multilingual_value")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TMultilingualValue extends BaseEntity {
  @EmbeddedId
  private TMultilingualValueId id;

  @MapsId("multilingualKey")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Comment("다국어 키 (기본 테이블 FK)")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "multilingual_key", nullable = false)
  private TMultilingualBase multilingualKey;

  @MapsId("langCd")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Comment("언어 코드")
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "lang_cd", nullable = false)
  private TLangBase langCd;

  @Size(max = 2000)
  @NotNull
  @Comment("다국어 값 (번역 텍스트)")
  @Column(name = "multilingual_val", nullable = false, length = 2000)
  private String multilingualVal;

}
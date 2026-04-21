package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Comment("다국어 베이스 테이블")
@Entity
@Table(name = "t_multilingual_base")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TMultilingualBase extends BaseEntity {
  @Id
  @Size(max = 6)
  @Comment("다국어 key")
  @Column(name = "multilingual_key", nullable = false, length = 6)
  private String multilingualKey;

  @Size(max = 2)
  @NotNull
  @Comment("다국어 유형 (S: SCREEN/ W : WORD/ M: MESSAGE / E: ERROR)")
  @Column(name = "multilingual_type", nullable = false, length = 2)
  private String multilingualType;

  @NotNull
  @Comment("사용 여부 (Y/N)")
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @Size(max = 500)
  @Comment("다국어 설명")
  @Column(name = "multilingual_desc", length = 500)
  private String multilingualDesc;

}
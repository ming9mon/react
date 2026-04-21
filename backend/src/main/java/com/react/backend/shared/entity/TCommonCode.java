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
@Comment("공통코드 상세 테이블")
@Entity
@Table(name = "t_common_code")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TCommonCode extends BaseEntity {
  @SequenceGenerator(name = "t_common_code_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @EmbeddedId
  private TCommonCodeId id;

  @MapsId("groupCode")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Comment("코드 그룹 코드")
  @JoinColumn(name = "group_code", nullable = false)
  private com.react.backend.shared.entity.TCommonCodeGroup groupCode;

  @MapsId("langCd")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Comment("언어 코드")
  @JoinColumn(name = "lang_cd", nullable = false)
  private TLangBase langCd;

  @Size(max = 500)
  @Comment("코드 설명")
  @Column(name = "code_desc", length = 500)
  private String codeDesc;

  @NotNull
  @Comment("정렬 순서")
  @Column(name = "sort_ord", nullable = false)
  private Integer sortOrd;

  @NotNull
  @Comment("사용 여부 Y/N")
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

}
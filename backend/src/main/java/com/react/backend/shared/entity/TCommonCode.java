package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "t_common_code")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TCommonCode extends BaseEntity {
  @EmbeddedId
  private TCommonCodeId id;

  @MapsId("groupCode")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_code", nullable = false)
  private TCommonCodeGroup groupCode;

  @Size(max = 100)
  @NotNull
  @Column(name = "code_text", nullable = false, length = 100)
  private String codeText;

  @Size(max = 500)
  @Column(name = "code_desc", length = 500)
  private String codeDesc;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "sort_no", nullable = false)
  private Integer sortNo;

}
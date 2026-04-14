package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_common_code")
@AttributeOverrides({
    @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY", length = 50))
})
public class TCommonCode extends BaseEntity {
  @EmbeddedId
  private TCommonCodeId id;

  @MapsId("groupCode")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "GROUP_CODE", nullable = false)
  private com.react.backend.shared.entity.TCommonCodeGroup groupCode;

  @Size(max = 100)
  @NotNull
  @Column(name = "CODE_TEXT", nullable = false, length = 100)
  private String codeText;

  @Size(max = 500)
  @Column(name = "CODE_DESC", length = 500)
  private String codeDesc;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "USE_YN", nullable = false)
  private Character useYn;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "SORT_NO", nullable = false)
  private Integer sortNo;

  @NotNull
  @ColumnDefault("current_timestamp()")
  @Column(name = "CREATE_DATE", nullable = false)
  private Instant createDate;

  @Size(max = 50)
  @Column(name = "CREATE_ID", length = 50)
  private String createId;

  @NotNull
  @ColumnDefault("current_timestamp()")
  @Column(name = "UPDATE_DATE", nullable = false)
  private Instant updateDate;

}
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
@Table(name = "t_common_code_group")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "CREATED_AT", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY", nullable = false, length = 50)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "UPDATED_AT", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY", length = 50))
})
public class TCommonCodeGroup extends BaseEntity {
  @Id
  @Size(max = 50)
  @Column(name = "GROUP_CD", nullable = false, length = 50)
  private String groupCd;

  @Size(max = 500)
  @Column(name = "GROUP_DESC", length = 500)
  private String groupDesc;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "USE_YN", nullable = false)
  private Character useYn;

}
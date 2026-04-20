package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_common_code_group")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TCommonCodeGroup extends BaseEntity {
  @Id
  @Size(max = 50)
  @Column(name = "group_code", nullable = false, length = 50)
  private String groupCode;

  @Size(max = 500)
  @Column(name = "group_desc", length = 500)
  private String groupDesc;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @OneToMany(mappedBy = "groupCode")
  private Set<TCommonCode> tCommonCodes = new LinkedHashSet<>();

}
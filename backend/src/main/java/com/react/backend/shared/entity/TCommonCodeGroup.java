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
@Comment("공통코드 그룹 테이블")
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
  @SequenceGenerator(name = "t_common_code_group_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Comment("코드 그룹 코드")
  @Column(name = "group_code", nullable = false, length = 50)
  private String groupCode;

  @Size(max = 500)
  @Comment("코드 그룹 설명")
  @Column(name = "group_desc", length = 500)
  private String groupDesc;

  @NotNull
  @Comment("사용 여부 Y/N")
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

}
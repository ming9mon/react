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
@Comment("언어 코드 테이블")
@Entity
@Table(name = "t_lang_base")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TLangBase extends BaseEntity {
  @Id
  @Size(max = 10)
  @SequenceGenerator(name = "t_lang_base_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Comment("언어 코드")
  @Column(name = "lang_cd", nullable = false, length = 10)
  private String langCd;

  @Size(max = 20)
  @NotNull
  @Comment("언어 명")
  @Column(name = "lang_nm", nullable = false, length = 20)
  private String langNm;

  @NotNull
  @Comment("사용 여부 (Y/N)")
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

}
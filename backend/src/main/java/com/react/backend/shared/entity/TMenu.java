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
@Comment("메뉴 정보 테이블")
@Entity
@Table(name = "t_menu")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TMenu extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_menu_id_gen")
  @SequenceGenerator(name = "t_menu_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Comment("메뉴 ID (PK)")
  @Column(name = "menu_id", nullable = false)
  private Long id;

  @Comment("부모 메뉴 ID")
  @Column(name = "parent_menu_id")
  private Long parentMenuId;

  @Size(max = 6)
  @NotNull
  @Comment("메뉴 다국어 코드")
  @Column(name = "menu_multilingual_cd", nullable = false, length = 6)
  private String menuMultilingualCd;

  @Size(max = 200)
  @NotNull
  @Comment("메뉴 경로")
  @Column(name = "menu_path", nullable = false, length = 200)
  private String menuPath;

  @NotNull
  @Comment("사용 여부 (Y/N)")
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @NotNull
  @Comment("정렬 순서")
  @Column(name = "sort_ord", nullable = false)
  private Integer sortOrd;

}
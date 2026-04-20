package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
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
  @SequenceGenerator(name = "t_menu_id_gen", sequenceName = "seq_menu", allocationSize = 1)
  @Column(name = "menu_id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "parent_menu_id")
  private TMenu parentMenu;

  @Size(max = 6)
  @NotNull
  @Column(name = "menu_multilingual_cd", nullable = false, length = 6)
  private String menuMultilingualCd;

  @Size(max = 200)
  @NotNull
  @Column(name = "menu_path", nullable = false, length = 200)
  private String menuPath;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @NotNull
  @Column(name = "sort_ord", nullable = false)
  private Integer sortOrd;

  @OneToMany(mappedBy = "parentMenu")
  private Set<TMenu> tMenus = new LinkedHashSet<>();

}
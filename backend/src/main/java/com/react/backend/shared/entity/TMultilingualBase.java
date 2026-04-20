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
@Table(name = "t_multilingual_base", uniqueConstraints = {
    @UniqueConstraint(name = "uk_t_multilingual_base_01", columnNames = {"multilingual_cd"})
})
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TMultilingualBase extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_multilingual_base_id_gen")
  @SequenceGenerator(name = "t_multilingual_base_id_gen", sequenceName = "seq_multilingual", allocationSize = 1)
  @Column(name = "multilingual_seq", nullable = false)
  private Long id;

  @Size(max = 6)
  @NotNull
  @Column(name = "multilingual_cd", nullable = false, length = 6)
  private String multilingualCd;

  @Size(max = 20)
  @NotNull
  @Column(name = "multilingual_type_cd", nullable = false, length = 20)
  private String multilingualTypeCd;

  @NotNull
  @ColumnDefault("'Y'")
  @Column(name = "use_yn", nullable = false, length = Integer.MAX_VALUE)
  private String useYn;

  @Size(max = 500)
  @Column(name = "multilingual_desc", length = 500)
  private String multilingualDesc;

  @OneToMany(mappedBy = "multilingualBase")
  private Set<TMultilingualValue> tMultilingualValues = new LinkedHashSet<>();

}
package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "t_multilingual_value")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TMultilingualValue extends BaseEntity {
  @SequenceGenerator(name = "t_multilingual_value_id_gen", sequenceName = "seq_multilingual", allocationSize = 1)
  @EmbeddedId
  private TMultilingualValueId id;

  @MapsId("langCd")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "lang_cd", nullable = false)
  private TLangBase langCd;

  @Size(max = 2000)
  @NotNull
  @Column(name = "multilingual_val", nullable = false, length = 2000)
  private String multilingualVal;

}
package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_user", uniqueConstraints = {
    @UniqueConstraint(name = "uk_t_user_id", columnNames = {"user_id"}),
    @UniqueConstraint(name = "uk_t_user_email", columnNames = {"email"})
})
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by", nullable = false)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by", nullable = false))
})
public class TUser extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_user_id_gen")
  @SequenceGenerator(name = "t_user_id_gen", sequenceName = "seq_user", initialValue = 10000000, allocationSize = 1)
  @Column(name = "user_seq", nullable = false)
  private Long id;

  @Size(max = 50)
  @NotNull
  @Column(name = "user_id", nullable = false, length = 50)
  private String userId;

  @Size(max = 255)
  @NotNull
  @Column(name = "passwd", nullable = false)
  private String passwd;

  @Size(max = 50)
  @NotNull
  @Column(name = "user_nm", nullable = false, length = 50)
  private String userNm;

  @Size(max = 50)
  @NotNull
  @Column(name = "nickname", nullable = false, length = 50)
  private String nickname;

  @Size(max = 2)
  @NotNull
  @Column(name = "provider_type_cd", nullable = false, length = 2)
  private String providerTypeCd;

  @Size(max = 100)
  @NotNull
  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Size(max = 1)
  @NotNull
  @Column(name = "sex", nullable = false, length = 1)
  private String sex;

  @Size(max = 10)
  @NotNull
  @Column(name = "lang_cd", nullable = false, length = 10)
  private String langCd;

  @Size(max = 255)
  @Column(name = "profile_img_url")
  private String profileImgUrl;

  @Size(max = 500)
  @Column(name = "refresh_token", length = 500)
  private String refreshToken;

  @OneToMany(mappedBy = "userSeq")
  private Set<TLoginHistory> tLoginHistories = new LinkedHashSet<>();

}
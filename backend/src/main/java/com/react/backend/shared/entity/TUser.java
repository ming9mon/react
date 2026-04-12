package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_user", uniqueConstraints = {
    @UniqueConstraint(name = "USER_ID", columnNames = {"USER_ID"}),
    @UniqueConstraint(name = "USER_NM", columnNames = {"USER_NM"})
})
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "CREATED_AT", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY", nullable = false, length = 50)),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "UPDATED_AT", nullable = false)),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY", nullable = false, length = 50))
})
public class TUser extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "USER_SEQ", nullable = false)
  private Long id;

  @Size(max = 50)
  @Column(name = "USER_ID", length = 50)
  private String userId;

  @Size(max = 255)
  @NotNull
  @Column(name = "PASSWD", nullable = false)
  private String passwd;

  @Size(max = 50)
  @NotNull
  @Column(name = "USER_NM", nullable = false, length = 50)
  private String userNm;

  @Size(max = 50)
  @Column(name = "NICKNAME", length = 50)
  private String nickname;

  @NotNull
  @Column(name = "PROVIDER_TYPE_CD", nullable = false)
  private Character providerTypeCd;

  @Size(max = 100)
  @Column(name = "EMAIL", length = 100)
  private String email;

  @Lob
  @Column(name = "SEX")
  private String sex;

  @Size(max = 255)
  @Column(name = "PROFILE_IMG_URL")
  private String profileImgUrl;

  @Size(max = 255)
  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;

}
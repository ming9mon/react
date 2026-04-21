package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Comment("사용자 정보 테이블")
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
  @SequenceGenerator(name = "t_user_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Comment("사용자 고유 SEQ")
  @Column(name = "user_seq", nullable = false)
  private Long id;

  @Size(max = 50)
  @NotNull
  @Comment("사용자 아이디")
  @Column(name = "user_id", nullable = false, length = 50)
  private String userId;

  @Size(max = 255)
  @NotNull
  @Comment("비밀번호")
  @Column(name = "passwd", nullable = false)
  private String passwd;

  @Size(max = 50)
  @NotNull
  @Comment("사용자 이름")
  @Column(name = "user_nm", nullable = false, length = 50)
  private String userNm;

  @Size(max = 50)
  @NotNull
  @Comment("닉네임")
  @Column(name = "nickname", nullable = false, length = 50)
  private String nickname;

  @Size(max = 2)
  @NotNull
  @Comment("로그인 제공자 유형 코드(LOCAL, KAKAO, GOOGLE, NAVER)")
  @Column(name = "provider_type_cd", nullable = false, length = 2)
  private String providerTypeCd;

  @Size(max = 100)
  @NotNull
  @Comment("이메일 주소")
  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Size(max = 1)
  @NotNull
  @Comment("성별(M, F)")
  @Column(name = "sex", nullable = false, length = 1)
  private String sex;

  @NotNull
  @Column(name = "lang_cd", nullable = false)
  private String langCd;

  @Size(max = 255)
  @Comment("프로필 이미지 URL")
  @Column(name = "profile_img_url")
  private String profileImgUrl;

  @Size(max = 500)
  @Comment("리프레시 토큰")
  @Column(name = "refresh_token", length = 500)
  private String refreshToken;

}
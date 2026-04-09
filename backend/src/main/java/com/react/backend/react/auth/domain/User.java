package com.react.backend.react.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "T_USER")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userSeq;  // 사용자 고유 ID (PK)

  @Column(nullable = false, unique = true, length = 50)
  private String userId; // 사용자 ID

  @Column(nullable = false, length = 255)
  private String passWd; // 비밀번호

  @Column(nullable = false, unique = true, length = 50)
  private String userNm; // 사용자 이름

  @Column(nullable = false, unique = true, length = 50)
  private String nickname; // 닉네임

  @Column(length = 100)
  private String email; // 이메일 주소

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "ENUM('M', 'F')")
  private Sex sex; // 성별

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private Provider provider = Provider.LOCAL; // 로그인 제공자, default: LOCAL

  private String providerId; // 소셜 로그인 제공자별 고유 ID

  private String profileImgUrl; // 프로필 이미지 URL

  private String refreshToken; // 리프레시 토큰

  private LocalDateTime crtdDt;

  private LocalDateTime updtDt;

  @PrePersist
  protected void onCreate() {
    this.crtdDt = LocalDateTime.now();
    this.updtDt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updtDt = LocalDateTime.now();
  }

  public enum Sex {
    M, F
  }

  public enum Provider {
    LOCAL, KAKAO, GOOGLE, NAVER
  }
}

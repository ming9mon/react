package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Setter
@Comment("로그인 이력 테이블")
@Entity
@Table(name = "t_login_history")
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false)),
    @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
    @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by"))
})
public class TLoginHistory extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_login_history_id_gen")
  @SequenceGenerator(name = "t_login_history_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Comment("로그인 이력 일련번호")
  @Column(name = "login_hist_seq", nullable = false)
  private Long id;

  @NotNull
  @Comment("사용자 SEQ")
  @Column(name = "user_seq", nullable = false)
  private Long userSeq;

  @NotNull
  @Comment("로그인 시도 일시")
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "login_date", nullable = false)
  private Instant loginDate;

  @Size(max = 2)
  @NotNull
  @Comment("로그인 결과 코드")
  @Column(name = "login_result_cd", nullable = false, length = 2)
  private String loginResultCd;

  @Size(max = 500)
  @Comment("로그인 결과 메시지")
  @Column(name = "login_result_msg", length = 500)
  private String loginResultMsg;

  @Size(max = 1)
  @NotNull
  @Comment("로그인 제공자 유형 코드(LOCAL, KAKAO, GOOGLE, NAVER)")
  @Column(name = "provider_type_cd", nullable = false, length = 1)
  private String providerTypeCd;

  @Size(max = 45)
  @Comment("접속 IP 주소")
  @Column(name = "access_ip", length = 45)
  private String accessIp;

  @Size(max = 1000)
  @Comment("사용자 에이전트 정보")
  @Column(name = "user_agent", length = 1000)
  private String userAgent;

  @Size(max = 2)
  @Comment("디바이스 유형 코드(PC, MOBILE, TABLET, ETC)")
  @Column(name = "device_type_cd", length = 2)
  private String deviceTypeCd;

  @Size(max = 100)
  @Comment("운영체제 정보")
  @Column(name = "os_info", length = 100)
  private String osInfo;

  @Size(max = 100)
  @Comment("브라우저 정보")
  @Column(name = "browser_info", length = 100)
  private String browserInfo;

}
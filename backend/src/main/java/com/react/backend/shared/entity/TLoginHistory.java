package com.react.backend.shared.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_login_history")
public class TLoginHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LOGIN_HIST_SEQ", nullable = false)
  private Long id;

  @Size(max = 50)
  @NotNull
  @Column(name = "USER_SEQ", nullable = false, length = 50)
  private String userSeq;

  @NotNull
  @ColumnDefault("current_timestamp()")
  @Column(name = "LOGIN_DATE", nullable = false)
  private Instant loginDate;

  @Size(max = 20)
  @NotNull
  @Column(name = "LOGIN_RESULT_CD", nullable = false, length = 20)
  private String loginResultCd;

  @Size(max = 500)
  @Column(name = "LOGIN_RESULT_MSG", length = 500)
  private String loginResultMsg;

  @NotNull
  @Column(name = "PROVIDER_TYPE_CD", nullable = false)
  private Character providerTypeCd;

  @Size(max = 45)
  @Column(name = "ACCESS_IP", length = 45)
  private String accessIp;

  @Size(max = 1000)
  @Column(name = "USER_AGENT", length = 1000)
  private String userAgent;

  @Column(name = "DEVICE_TYPE_CD")
  private Character deviceTypeCd;

  @Size(max = 100)
  @Column(name = "OS_INFO", length = 100)
  private String osInfo;

  @Size(max = 100)
  @Column(name = "BROWSER_INFO", length = 100)
  private String browserInfo;

}
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_login_history_id_gen")
  @SequenceGenerator(name = "t_login_history_id_gen", sequenceName = "seq_login_hist", allocationSize = 1)
  @Column(name = "login_hist_seq", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_seq", nullable = false)
  private TUser userSeq;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "login_date", nullable = false)
  private Instant loginDate;

  @Size(max = 20)
  @NotNull
  @Column(name = "login_result_cd", nullable = false, length = 20)
  private String loginResultCd;

  @Size(max = 500)
  @Column(name = "login_result_msg", length = 500)
  private String loginResultMsg;

  @Size(max = 1)
  @NotNull
  @Column(name = "provider_type_cd", nullable = false, length = 1)
  private String providerTypeCd;

  @Size(max = 45)
  @Column(name = "access_ip", length = 45)
  private String accessIp;

  @Size(max = 1000)
  @Column(name = "user_agent", length = 1000)
  private String userAgent;

  @Size(max = 2)
  @Column(name = "device_type_cd", length = 2)
  private String deviceTypeCd;

  @Size(max = 100)
  @Column(name = "os_info", length = 100)
  private String osInfo;

  @Size(max = 100)
  @Column(name = "browser_info", length = 100)
  private String browserInfo;

}
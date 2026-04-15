package com.react.backend.configuration.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareService implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof Long)) {
      return Optional.of(0L);
    }

    return Optional.of((Long) auth.getPrincipal());
  }
}

package com.react.backend.configuration.init;

import com.react.backend.shared.entity.TLangBase;
import com.react.backend.shared.repository.LangBaseRepository;
import com.react.backend.shared.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 초기 데이터 설정
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final LangBaseRepository langBaseRepository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        initLangBase();
        initSystemUser();
    }

    private void initLangBase() {
        if (langBaseRepository.count() > 0) {
            return;
        }

        TLangBase korean = new TLangBase();
        korean.setLangCd("ko_KR");
        korean.setLangNm("한국어");
        korean.setUseYn("Y");

        langBaseRepository.save(korean);
        log.info("기본 언어 데이터 등록 : ko_KR (한국어)");
    }

    private void initSystemUser() {
        if (userRepository.existsById(0L)) {
            return;
        }

        jdbcTemplate.update("""
                INSERT INTO t_user (
                    user_seq, user_id, passwd, user_nm, nickname,
                    provider_type_cd, email, sex, lang_cd,
                    created_at, created_by, updated_at, updated_by
                ) VALUES (0, 'system', '-', 'System', 'System', 'S', 'system@system.com', 'M', 'ko_KR', NOW(), 0, NOW(), 0)
                """);
        log.info("시스템 유저 등록 완료 : user_seq=0, user_id=system");
    }
}
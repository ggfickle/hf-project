package com.hf.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/7 23:38
 */
@Configuration
public class JPAConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("大师");
    }
}

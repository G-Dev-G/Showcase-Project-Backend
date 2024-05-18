package com.project.backend.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@Configuration
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 1800) // Session timeout seconds
public class SessionConfig {

    /**
     * Detect "token" in http headers as to authenticate sessionID
     * @return HeaderHttpSessionIdResolver
     */
    @Bean
    public HeaderHttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver("token");
    }
}

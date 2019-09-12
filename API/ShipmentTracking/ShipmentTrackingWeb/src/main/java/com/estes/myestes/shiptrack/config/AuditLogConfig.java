package com.estes.myestes.shiptrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;

import com.estes.myestes.MyEstesAuditLog.MyEstesAuditLogger;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=false)
public class AuditLogConfig {
    @Bean
    public MyEstesAuditLogger getMyEstesAuditLogger(JdbcTemplate jdbcTemplate){
        return new MyEstesAuditLogger(jdbcTemplate);
    }
}
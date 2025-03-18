package com.hongphat.authservice.config;

import com.hongphat.authservice.service.TokenBlacklistService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * The type Scheduling config.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Instantiates a new Scheduling config.
     *
     * @param tokenBlacklistService the token blacklist service
     */
    public SchedulingConfig(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    /**
     * Cleanup expired tokens.
     */
    @Scheduled(fixedRate = 3600000)
    public void cleanupExpiredTokens() {
        tokenBlacklistService.cleanupExpiredTokens();
    }
}
package com.hongphat.common_service.model;

import lombok.*;

/**
 * --------------------------------------------------------
 * EmailEvent
 *
 * @author: phatthh
 * @since: 16 /03/2025
 * @description: Dự án microservice của Phát --------------------------------------------------------
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailEvent {
    private String to;
    private String subject;
    private String content;
    private boolean isHtml;
    private EmailType type;

    /**
     * The enum Email type.
     */
    public enum EmailType {
        /**
         * Registration email type.
         */
        REGISTRATION,
        /**
         * Password reset email type.
         */
        PASSWORD_RESET
    }
}

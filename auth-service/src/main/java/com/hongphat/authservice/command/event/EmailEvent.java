package com.hongphat.authservice.command.event;

import lombok.*;

/**
 * --------------------------------------------------------
 * EmailEvent
 *
 * @author: phatthh
 * @since: 16/03/2025
 * @description: Dự án microservice của Phát
 * --------------------------------------------------------
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

    public enum EmailType {
        REGISTRATION,
        PASSWORD_RESET
    }
}

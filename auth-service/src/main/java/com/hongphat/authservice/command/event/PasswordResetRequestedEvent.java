package com.hongphat.authservice.command.event;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordResetRequestedEvent {
    private String id;
    private String email;
    private String resetToken;
}
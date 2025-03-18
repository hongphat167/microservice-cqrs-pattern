package com.hongphat.authservice.command.event;

import lombok.*;

/**
 * The type User registered event.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisteredEvent {
    private String id;
    private String username;
    private String email;
    private String hashedPassword;
}
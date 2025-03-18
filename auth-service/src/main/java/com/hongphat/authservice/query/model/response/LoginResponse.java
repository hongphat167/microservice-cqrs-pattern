package com.hongphat.authservice.query.model.response;

import lombok.*;

/**
 * The type Login response.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {
    private String token;
}

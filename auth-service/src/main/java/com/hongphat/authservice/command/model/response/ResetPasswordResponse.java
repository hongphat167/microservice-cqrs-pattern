package com.hongphat.authservice.command.model.response;

import lombok.*;

/**
 * --------------------------------------------------------
 * ResetPasswordResponse
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
public class ResetPasswordResponse {
    private String messageResetPassword;
}

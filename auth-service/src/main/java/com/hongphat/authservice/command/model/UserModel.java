package com.hongphat.authservice.command.model;

import lombok.*;

/**
 * UserModel
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserModel {
    private String id;
    private String username;
    private String email;
    private String password;
    private String resetToken;
    private Long resetTokenExpiry;
}
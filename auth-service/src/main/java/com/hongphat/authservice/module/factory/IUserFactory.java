package com.hongphat.authservice.module.factory;

import com.hongphat.authservice.command.model.UserModel;
import com.hongphat.common_service.common.BaseCrudFactory;

import java.time.Instant;

public interface IUserFactory extends BaseCrudFactory<UserModel, UserModel> {

    /**
     * Update password reset token.
     *
     * @param email       the email
     * @param resetToken  the reset token
     * @param tokenExpiry the token expiry
     */
    void updatePasswordResetToken(String email, String resetToken, Instant tokenExpiry);

    /**
     * Find by username.
     *
     * @param username the username
     * @return the user model
     */
    UserModel findByUsername(String username);

    /**
     * Find by email.
     *
     * @param email the email
     * @return
     */
    UserModel findByEmail(String email);

    /**
     * Find by reset token.
     *
     * @param resetToken the reset token
     * @return the user model
     */
    UserModel findByResetToken(String resetToken);
}
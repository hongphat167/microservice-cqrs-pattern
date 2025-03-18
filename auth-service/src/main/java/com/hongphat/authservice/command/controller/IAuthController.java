package com.hongphat.authservice.command.controller;

import com.hongphat.authservice.command.model.request.ForgotPasswordRequest;
import com.hongphat.authservice.command.model.request.RegisterRequest;
import com.hongphat.authservice.command.model.request.ResetPasswordRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Auth controller.
 */
public interface IAuthController {
    /**
     * Register auth response.
     *
     * @param request the request
     * @return the auth response
     */
    @PostMapping("/register")
    String register(@Valid @RequestBody RegisterRequest request);

    /**
     * Request password reset string.
     *
     * @param request the request
     * @return the string
     */
    @PostMapping("/forgot-password")
    String requestPasswordReset(@Valid @RequestBody ForgotPasswordRequest request);

    /**
     * Reset password string.
     *
     * @param request the request
     * @return the string
     */
    @PostMapping("/reset-password")
    String resetPassword(@Valid @RequestBody ResetPasswordRequest request);
}

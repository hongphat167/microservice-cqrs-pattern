package com.hongphat.authservice.query.controller;

import com.hongphat.authservice.query.model.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * The interface Login controller.
 */
public interface ILoginController {
    /**
     * Login login response.
     *
     * @param request the request
     * @return the login response
     */
    @PostMapping("/login")
    String login(@Valid @RequestBody LoginRequest request);

    /**
     * Logout response entity.
     *
     * @param token the token
     * @return the response entity
     */
    @PostMapping("/logout")
    String logout(@RequestHeader("Authorization") String token);
}

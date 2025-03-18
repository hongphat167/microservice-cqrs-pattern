package com.hongphat.authservice.query.controller.impl;

import com.hongphat.authservice.query.controller.ILoginController;
import com.hongphat.authservice.query.model.request.LoginRequest;
import com.hongphat.authservice.query.queries.LoginQuery;
import com.hongphat.authservice.service.JwtService;
import com.hongphat.authservice.service.TokenBlacklistService;
import com.hongphat.common_service.enums.ResponseCode;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseError;
import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseSuccess;

/**
 * The type Login controller.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController implements ILoginController {
    private final QueryGateway queryGateway;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * Instantiates a new Login controller.
     *
     * @param queryGateway          the query gateway
     * @param jwtService            the jwt service
     * @param tokenBlacklistService the token blacklist service
     */
    protected LoginController(QueryGateway queryGateway,
                              JwtService jwtService,
                              TokenBlacklistService tokenBlacklistService) {
        this.queryGateway = queryGateway;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    public String login(LoginRequest request) {
        LoginQuery query = LoginQuery.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        return queryGateway.query(
                query, ResponseTypes.instanceOf(String.class)
        ).join();
    }

    @Override
    public String logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                long expirationTime = jwtService.getExpirationTimeFromToken(token);

                tokenBlacklistService.blacklistToken(token, expirationTime);

                return makeBaseResponseSuccess(
                        ResponseCode.SUCCESS.getCode(),
                        ResponseCode.SUCCESS.getDescription(),
                        null
                );
            } catch (Exception e) {
                return makeBaseResponseError(
                        ResponseCode.EXPIRED.getCode(), ResponseCode.EXPIRED.getDescription()
                );
            }
        }

        return makeBaseResponseError(
                ResponseCode.INVALID.getCode(), ResponseCode.INVALID.getDescription()
        );
    }
}
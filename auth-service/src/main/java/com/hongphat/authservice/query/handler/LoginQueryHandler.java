package com.hongphat.authservice.query.handler;

import com.hongphat.authservice.command.model.UserModel;
import com.hongphat.authservice.module.factory.IUserFactory;
import com.hongphat.authservice.query.model.response.LoginResponse;
import com.hongphat.authservice.query.queries.LoginQuery;
import com.hongphat.authservice.service.JwtService;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.enums.ResponseCode;
import com.hongphat.common_service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseError;
import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseSuccess;

/**
 * The type Login query handler.
 */
@Component
@Slf4j
public class LoginQueryHandler {
    private final IUserFactory userFactory;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Instantiates a new Login query handler.
     *
     * @param userFactory     the user factory
     * @param passwordEncoder the password encoder
     * @param jwtService      the jwt service
     */
    public LoginQueryHandler(IUserFactory userFactory,
                             PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Handle string.
     *
     * @param query the query
     * @return the string
     */
    @QueryHandler
    public String handle(LoginQuery query) {
        try {
            UserModel user = userFactory.findByUsername(query.getUsername());

            boolean isPasswordValid = passwordEncoder.matches(query.getPassword(), user.getPassword());
            if (!isPasswordValid) {
                throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
            }

            String token = jwtService.generateToken(user.getId(), user.getUsername());

            return makeBaseResponseSuccess(
                    ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDescription(),
                    LoginResponse.builder()
                            .token(token)
                            .build()
            );
        } catch (BusinessException e) {
            return makeBaseResponseError(
                    ResponseCode.INVALID.getCode(), ResponseCode.INVALID.getDescription()
            );
        }
    }
}

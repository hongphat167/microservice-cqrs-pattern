package com.hongphat.authservice.command.controller.impl;

import com.hongphat.authservice.command.command_handling.RegisterUserCommand;
import com.hongphat.authservice.command.command_handling.ResetPasswordCommand;
import com.hongphat.authservice.command.controller.IAuthController;
import com.hongphat.authservice.command.model.UserModel;
import com.hongphat.authservice.command.model.request.ForgotPasswordRequest;
import com.hongphat.authservice.command.model.request.RegisterRequest;
import com.hongphat.authservice.command.model.request.ResetPasswordRequest;
import com.hongphat.authservice.command.model.response.AuthResponse;
import com.hongphat.authservice.command.model.response.ForgotPasswordResponse;
import com.hongphat.authservice.command.model.response.ResetPasswordResponse;
import com.hongphat.authservice.module.factory.IUserFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.enums.ResponseCode;
import com.hongphat.common_service.exception.BusinessException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseError;
import static com.hongphat.common_service.model.BaseResponse.makeBaseResponseSuccess;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements IAuthController {

    private final CommandGateway commandGateway;
    private final IUserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Auth controller.
     *
     * @param commandGateway  the command gateway
     * @param userFactory     the user factory
     * @param passwordEncoder the password encoder
     */
    protected AuthController(CommandGateway commandGateway,
                             IUserFactory userFactory,
                             PasswordEncoder passwordEncoder) {
        this.commandGateway = commandGateway;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterRequest request) {
        try {
            String userId = UUID.randomUUID().toString();
            RegisterUserCommand command = RegisterUserCommand.builder()
                    .id(userId)
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .build();
            commandGateway.sendAndWait(command);

            AuthResponse response = AuthResponse.builder()
                    .userId(userId)
                    .build();

            return makeBaseResponseSuccess(
                    ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDescription(),
                    response
            );
        } catch (BusinessException e) {
            return makeBaseResponseError(
                    ResponseCode.NOT_FOUND.getCode(),
                    ResponseCode.NOT_FOUND.getDescription()
            );
        } catch (Exception e) {
            return makeBaseResponseError(
                    ResponseCode.ERROR.getCode(),
                    ResponseCode.ERROR.getDescription()
            );
        }
    }

    @Override
    public String requestPasswordReset(ForgotPasswordRequest request) {
        try {
            UserModel user = userFactory.findByEmail(request.getEmail());

            String resetToken = UUID.randomUUID().toString();

            ResetPasswordCommand command = ResetPasswordCommand.builder()
                    .id(user.getId())
                    .email(request.getEmail())
                    .resetToken(resetToken)
                    .build();

            commandGateway.sendAndWait(command);

            return makeBaseResponseSuccess(
                    ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDescription(),
                    ForgotPasswordResponse.builder()
                            .token(resetToken)
                            .build()
            );
        } catch (BusinessException e) {
            return makeBaseResponseError(
                    ResponseCode.ERROR.getCode(),
                    e.getMessage()
            );
        }
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        try {
            UserModel user = userFactory.findByResetToken(request.getToken());

            if (user.getResetTokenExpiry() == null ||
                    Instant.ofEpochMilli(user.getResetTokenExpiry()).isBefore(Instant.now())) {
                throw new BusinessException(ErrorCode.INVALID_RESET_TOKEN, "Reset token has expired");
            }

            String hashedPassword = this.passwordEncoder.encode(request.getNewPassword());

            UserModel updatedUser = user.toBuilder()
                    .password(hashedPassword)
                    .resetToken(null)
                    .resetTokenExpiry(null)
                    .build();

            userFactory.update(user.getId(), updatedUser);

            return makeBaseResponseSuccess(
                    ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getDescription(),
                    ResetPasswordResponse.builder()
                            .messageResetPassword("Password reset successfully")
                            .build()
            );
        } catch (BusinessException e) {
            return makeBaseResponseError(
                    ResponseCode.INVALID.getCode(),
                    e.getMessage()
            );
        } catch (Exception e) {
            return makeBaseResponseError(
                    ResponseCode.ERROR.getCode(),
                    ResponseCode.ERROR.getDescription()
            );
        }
    }
}

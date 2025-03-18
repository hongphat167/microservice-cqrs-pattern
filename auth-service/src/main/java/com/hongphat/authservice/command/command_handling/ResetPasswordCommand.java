package com.hongphat.authservice.command.command_handling;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * The type Reset password command.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordCommand {
    @TargetAggregateIdentifier
    private String id;
    private String email;
    private String resetToken;
}
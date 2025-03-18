package com.hongphat.authservice.module.factory.impl;

import com.hongphat.authservice.command.model.UserModel;
import com.hongphat.authservice.module.entity.UserEntity;
import com.hongphat.authservice.module.factory.IUserFactory;
import com.hongphat.authservice.module.repository.IUserRepository;
import com.hongphat.common_service.common.BaseFactory;
import com.hongphat.common_service.enumerate.ErrorCode;
import com.hongphat.common_service.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * UserFactory
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 */
@Component
@Slf4j
public class UserFactory extends BaseFactory<
        UserEntity, UserModel,
        IUserRepository,
        String
        > implements IUserFactory {

    /**
     * Instantiates a new User factory.
     *
     * @param repository the repository
     */
    protected UserFactory(IUserRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public void create(UserModel model) {
        if (isNull(model)) {
            return;
        }

        if (repository.existsByUsername(model.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS, "Username already exists");
        }

        if (repository.existsByEmail(model.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS, "Email already exists");
        }

        repository.save(toEntity(model));
    }

    @Override
    public UserModel get(String id) {
        return toModelOptional(repository.findById(id).orElse(null))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "User not found with id: " + id));
    }

    @Override
    public List<UserModel> getList() {
        return toModel(repository.findAll());
    }

    @Override
    public void update(String id, UserModel model) {
        repository.findById(id)
                .map(existingEntity -> {
                    UserEntity updated = updateEntity(existingEntity, model);
                    log.info("Updating user: {} with new password hash: {}",
                            id, updated.getPassword().substring(0, 10) + "...");
                    return updated;
                })
                .ifPresent(repository::save);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void updatePasswordResetToken(String email, String resetToken, Instant tokenExpiry) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "User not found with email: " + email));

        user = user.toBuilder()
                .resetToken(resetToken)
                .resetTokenExpiry(tokenExpiry.toEpochMilli())
                .build();

        repository.save(user);
    }

    @Override
    public UserModel findByUsername(String username) {
        return toModelOptional(repository.findByUsername(username).orElse(null))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "User not found with username: " + username));
    }

    @Override
    public UserModel findByEmail(String email) {
        return toModelOptional(repository.findByEmail(email).orElse(null))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND,
                        "User not found with email: " + email));
    }

    @Override
    public UserModel findByResetToken(String resetToken) {
        return toModelOptional(repository.findByResetToken(resetToken).orElse(null))
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_RESET_TOKEN, "Invalid or expired reset token"));
    }

    @Override
    public UserModel toModel(UserEntity entity) {
        if (isNull(entity)) {
            return null;
        }
        return UserModel.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .resetToken(entity.getResetToken())
                .resetTokenExpiry(entity.getResetTokenExpiry())
                .build();
    }

    @Override
    public UserEntity toEntity(UserModel model) {
        if (isNull(model)) {
            return null;
        }
        return UserEntity.builder()
                .id(model.getId())
                .username(model.getUsername())
                .email(model.getEmail())
                .password(model.getPassword())
                .resetToken(model.getResetToken())
                .resetTokenExpiry(model.getResetTokenExpiry())
                .build();
    }

    @Override
    protected UserEntity update(UserEntity existingEntity, UserModel model) {
        return existingEntity.toBuilder()
                .username(model.getUsername())
                .email(model.getEmail())
                .password(model.getPassword())
                .resetToken(model.getResetToken())
                .resetTokenExpiry(model.getResetTokenExpiry())
                .build();
    }
}
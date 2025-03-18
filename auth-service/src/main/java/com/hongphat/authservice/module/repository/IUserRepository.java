package com.hongphat.authservice.module.repository;

import com.hongphat.authservice.module.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {
    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find by reset token optional.
     *
     * @param resetToken the reset token
     * @return the optional
     */
    Optional<UserEntity> findByResetToken(String resetToken);

    /**
     * Exists by username boolean.
     *
     * @param username the username
     * @return the boolean
     */
    boolean existsByUsername(String username);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);
}

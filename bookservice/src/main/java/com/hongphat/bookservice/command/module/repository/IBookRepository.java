package com.hongphat.bookservice.command.module.repository;

import com.hongphat.bookservice.command.module.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Book repository.
 *
 * @author hongp
 * @createDay 05/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Repository
public interface IBookRepository extends JpaRepository<BookEntity, String> {
}
package com.hongphat.bookservice.command.module.factory.impl;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.command.module.entity.BookEntity;
import com.hongphat.bookservice.command.module.factory.IBookFactory;
import com.hongphat.bookservice.command.module.repository.IBookRepository;
import com.hongphat.common_service.common.BaseFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Book factory.
 *
 * @author hongp
 * @createDay 05/01/2025
 * @description Happy Coding With Phat 😊😊
 */
@Component
public class BookFactory extends BaseFactory<BookEntity, BookModel>
		implements IBookFactory {

	private final IBookRepository bookRepository;

	/**
	 * Instantiates a new Book factory.
	 *
	 * @param bookRepository the book repository
	 */
	protected BookFactory(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void createAndSave(BookCreateEvent eventListener) {
		if (eventListener == null) {
			return;
		}
		BookModel model = BookModel.builder()
				.id(eventListener.getId())
				.name(eventListener.getName())
				.author(eventListener.getAuthor())
				.isReady(true)
				.build();

		bookRepository.save(toEntity(model));
	}

	@Override
	public BookModel findById(String id) {
		return toModelOptional(bookRepository.findById(id).orElse(null))
				.orElse(null);
	}

	@Override
	public List<BookModel> findAll() {
		return toModel(bookRepository.findAll());
	}

	@Override
	public void updateBook(String id, BookModel model) {
		bookRepository.findById(id)
				.map(existingEntity -> updateEntity(existingEntity, model))
				.ifPresent(bookRepository::save);
	}

	@Override
	public void deleteById(String id) {
		bookRepository.deleteById(id);
	}

	@Override
	public BookModel toModel(BookEntity entity) {
		if (isNull(entity)) {
			return null;
		}
		return BookModel.builder()
				.id(entity.getId())
				.name(entity.getName())
				.author(entity.getAuthor())
				.isReady(entity.getIsReady())
				.build();
	}

	@Override
	public BookEntity toEntity(BookModel model) {
		if (isNull(model)) {
			return null;
		}
		return BookEntity.builder()
				.id(model.getId())
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(model.getIsReady())
				.build();
	}

	/**
	 * Update existing entity with model data.
	 *
	 * @param existingEntity the existing book entity
	 * @param model          the book model containing updated data
	 * @return the updated book entity
	 */
	@Override
	protected BookEntity update(BookEntity existingEntity, BookModel model) {
		return existingEntity.toBuilder()
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(model.getIsReady())
				.build();
	}
}
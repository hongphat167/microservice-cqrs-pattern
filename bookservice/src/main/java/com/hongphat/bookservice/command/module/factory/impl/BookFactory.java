package com.hongphat.bookservice.command.module.factory;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.command.module.entity.BookEntity;
import com.hongphat.bookservice.command.module.repository.IBookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Book factory.
 */
@Component
public class BookFactory implements IBookFactory {

	private final IBookRepository bookRepository;

	/**
	 * Instantiates a new Book factory.
	 *
	 * @param bookRepository the book repository
	 */
	public BookFactory(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void createAndSave(BookCreateEvent eventListener) {
		BookModel model = createFromEvent(eventListener);
		BookEntity entity = toEntity(model);
		toModel(bookRepository.save(entity));
	}

	@Override
	public BookModel findById(String id) {
		BookEntity entity = bookRepository.findById(id).orElse(null);
		return toModel(entity);
	}

	@Override
	public List<BookModel> findAll() {
		return bookRepository.findAll()
				.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	@Override
	public void updateBook(String id, BookModel model) {
		bookRepository.findById(id)
				.map(existingEntity -> update(existingEntity, model))
				.map(bookRepository::save)
				.map(this::toModel);
	}

	@Override
	public void deleteById(String id) {
		bookRepository.deleteById(id);
	}

	private BookModel createFromEvent(BookCreateEvent eventListener) {
		if (eventListener == null) {
			return null;
		}
		return BookModel.builder()
				.id(eventListener.getId())
				.name(eventListener.getName())
				.author(eventListener.getAuthor())
				.isReady(true)
				.build();
	}

	private BookModel toModel(BookEntity entity) {
		if (entity == null) {
			return null;
		}
		return BookModel.builder()
				.id(entity.getId())
				.name(entity.getName())
				.author(entity.getAuthor())
				.isReady(entity.getIsReady())
				.build();
	}

	private BookEntity toEntity(BookModel model) {
		if (model == null) {
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
	private BookEntity update(BookEntity existingEntity, BookModel model) {
		if (existingEntity == null || model == null) {
			return existingEntity;
		}

		return existingEntity.toBuilder()
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(model.getIsReady())
				.build();
	}
}
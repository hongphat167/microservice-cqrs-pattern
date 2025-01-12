package com.hongphat.bookservice.module.factory.impl;

import com.hongphat.bookservice.command.event.BookCreateEvent;
import com.hongphat.bookservice.command.model.BookModel;
import com.hongphat.bookservice.module.entity.BookEntity;
import com.hongphat.bookservice.module.factory.IBookFactory;
import com.hongphat.bookservice.module.repository.IBookRepository;
import com.hongphat.common_service.common.BaseFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Book factory.
 */
@Component
public class BookFactory extends BaseFactory<
		BookEntity, BookModel,
		IBookRepository,
		String
		>
		implements IBookFactory {

	/**
	 * Instantiates a new Book factory.
	 *
	 * @param repository the repository
	 */
	protected BookFactory(IBookRepository repository) {
		super(repository);
	}

	@Override
	public void create(BookCreateEvent event) {
		if (event == null) {
			return;
		}
		BookModel model = BookModel.builder()
				.id(event.getId())
				.name(event.getName())
				.author(event.getAuthor())
				.isReady(true)
				.build();

		repository.save(toEntity(model));
	}

	@Override
	public BookModel get(String id) {
		return toModelOptional(repository.findById(id).orElse(null))
				.orElse(null);
	}

	@Override
	public List<BookModel> getList() {
		return toModel(repository.findAll());
	}

	@Override
	public void update(String id, BookModel model) {
		repository.findById(id)
				.map(existingEntity -> updateEntity(existingEntity, model))
				.ifPresent(repository::save);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
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

	@Override
	protected BookEntity update(BookEntity existingEntity, BookModel model) {
		return existingEntity.toBuilder()
				.name(model.getName())
				.author(model.getAuthor())
				.isReady(model.getIsReady())
				.build();
	}
}
package com.hongphat.borrowservice.module.factory.impl;

import com.hongphat.borrowservice.command.event.CreateBorrowEvent;
import com.hongphat.borrowservice.command.model.BorrowModel;
import com.hongphat.borrowservice.module.entity.BorrowEntity;
import com.hongphat.borrowservice.module.factory.IBorrowFactory;
import com.hongphat.borrowservice.module.repository.IBorrowRepository;
import com.hongphat.common_service.common.BaseFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BorrowFactory
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 1 :51 CH 12/01/2025
 */
@Component
public class BorrowFactory extends BaseFactory<
		BorrowEntity, BorrowModel,
		IBorrowRepository,
		String
		> implements IBorrowFactory {
	/**
	 * Instantiates a new Base factory.
	 *
	 * @param repository the repository
	 */
	protected BorrowFactory(IBorrowRepository repository) {
		super(repository);
	}

	@Override
	public void create(CreateBorrowEvent event) {
		if (event == null) {
			return;
		}
		BorrowModel model = BorrowModel.builder()
				.id(event.getId())
				.bookId(event.getBookId())
				.employeeId(event.getEmployeeId())
				.borrowDate(event.getBorrowDate())
				.build();

		repository.save(toEntity(model));
	}

	@Override
	public BorrowModel get(String id) {
		return toModelOptional(repository.findById(id).orElse(null))
				.orElse(null);
	}

	@Override
	public List<BorrowModel> getList() {
		return toModel(repository.findAll());
	}

	@Override
	public void update(String id, BorrowModel model) {
		repository.findById(id)
				.map(existingEntity -> updateEntity(existingEntity, model))
				.ifPresent(repository::save);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public BorrowModel toModel(BorrowEntity entity) {
		if (isNull(entity)) {
			return null;
		}
		return BorrowModel.builder()
				.id(entity.getId())
				.bookId(entity.getBookId())
				.employeeId(entity.getEmployeeId())
				.borrowDate(entity.getBorrowDate())
				.returnDate(entity.getReturnDate())
				.build();
	}

	@Override
	public BorrowEntity toEntity(BorrowModel model) {
		if (isNull(model)) {
			return null;
		}
		return BorrowEntity.builder()
				.id(model.getId())
				.bookId(model.getBookId())
				.employeeId(model.getEmployeeId())
				.borrowDate(model.getBorrowDate())
				.returnDate(model.getReturnDate())
				.build();
	}

	@Override
	protected BorrowEntity update(BorrowEntity existingEntity, BorrowModel model) {
		return existingEntity.toBuilder()
				.bookId(model.getBookId())
				.employeeId(model.getEmployeeId())
				.borrowDate(model.getBorrowDate())
				.returnDate(model.getReturnDate())
				.build();
	}
}

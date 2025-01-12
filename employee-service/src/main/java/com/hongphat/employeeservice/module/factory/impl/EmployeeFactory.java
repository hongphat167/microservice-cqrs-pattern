package com.hongphat.employeeservice.module.factory.impl;

import com.hongphat.common_service.common.BaseFactory;
import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.module.entity.EmployeeEntity;
import com.hongphat.employeeservice.module.factory.IEmployeeFactory;
import com.hongphat.employeeservice.module.repository.IEmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * EmployeeFactory
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :53 CH 08/01/2025
 */
@Component
public class EmployeeFactory extends BaseFactory<
		EmployeeEntity, EmployeeModel,
		IEmployeeRepository,
		String
		> implements IEmployeeFactory {


	/**
	 * Instantiates a new Employee factory.
	 *
	 * @param repository the repository
	 */
	protected EmployeeFactory(IEmployeeRepository repository) {
		super(repository);
	}

	@Override
	public void create(CreateEmployeeEvent eventListener) {
		if (isNull(eventListener)) {
			return;
		}
		EmployeeModel model = EmployeeModel.builder()
				.id(eventListener.getId())
				.firstName(eventListener.getFirstName())
				.lastName(eventListener.getLastName())
				.kin(eventListener.getKin())
				.isDisciplined(eventListener.getIsDisciplined())
				.build();

		repository.save(toEntity(model));
	}

	@Override
	public EmployeeModel get(String id) {
		return toModelOptional(repository.findById(id).orElse(null))
				.orElse(null);
	}

	@Override
	public List<EmployeeModel> getList() {
		return toModel(repository.findAll());
	}

	@Override
	public void update(String id, EmployeeModel model) {
		repository.findById(id)
				.map(existingEntity -> updateEntity(existingEntity, model))
				.ifPresent(repository::save);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

	@Override
	public List<EmployeeModel> findByIsDisciplined(Boolean isDisciplined) {
		return toModel(repository.findByIsDisciplined(isDisciplined));
	}

	@Override
	public EmployeeModel toModel(EmployeeEntity entity) {
		if (isNull(entity)) {
			return null;
		}
		return EmployeeModel.builder()
				.id(entity.getId())
				.firstName(entity.getFirstName())
				.lastName(entity.getLastName())
				.kin(entity.getKin())
				.isDisciplined(entity.getIsDisciplined())
				.build();
	}

	@Override
	public EmployeeEntity toEntity(EmployeeModel model) {
		if (isNull(model)) {
			return null;
		}
		return EmployeeEntity.builder()
				.id(model.getId())
				.firstName(model.getFirstName())
				.lastName(model.getLastName())
				.kin(model.getKin())
				.isDisciplined(model.getIsDisciplined())
				.build();
	}

	@Override
	protected EmployeeEntity update(EmployeeEntity existingEntity, EmployeeModel model) {
		return existingEntity.toBuilder()
				.firstName(model.getFirstName())
				.lastName(model.getLastName())
				.kin(model.getKin())
				.isDisciplined(model.getIsDisciplined())
				.build();
	}
}
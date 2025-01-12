package com.hongphat.employeeservice.command.module.factory.impl;

import com.hongphat.common_service.common.BaseFactory;
import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.command.module.entity.EmployeeEntity;
import com.hongphat.employeeservice.command.module.factory.IEmployeeFactory;
import com.hongphat.employeeservice.command.module.repository.IEmployeeRepository;
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
public class EmployeeFactory extends BaseFactory<EmployeeEntity, EmployeeModel> implements IEmployeeFactory {

	private final IEmployeeRepository employeeRepository;

	/**
	 * Instantiates a new Employee factory.
	 *
	 * @param employeeRepository the employee repository
	 */
	protected EmployeeFactory(IEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void createAndSave(CreateEmployeeEvent eventListener) {
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

		employeeRepository.save(toEntity(model));
	}

	@Override
	public EmployeeModel findById(String id) {
		return toModelOptional(employeeRepository.findById(id).orElse(null))
				.orElse(null);
	}

	@Override
	public List<EmployeeModel> findAll() {
		return toModel(employeeRepository.findAll());
	}

	@Override
	public void updateEmployee(String id, EmployeeModel model) {
		employeeRepository.findById(id)
				.map(existingEntity -> updateEntity(existingEntity, model))
				.ifPresent(employeeRepository::save);
	}

	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<EmployeeModel> findByIsDisciplined(Boolean isDisciplined) {
		return toModel(employeeRepository.findByIsDisciplined(isDisciplined));
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

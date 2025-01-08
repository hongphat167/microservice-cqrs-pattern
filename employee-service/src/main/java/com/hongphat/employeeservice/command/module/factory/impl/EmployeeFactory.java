package com.hongphat.employeeservice.command.module.factory.impl;

import com.hongphat.employeeservice.command.event.CreateEmployeeEvent;
import com.hongphat.employeeservice.command.model.EmployeeModel;
import com.hongphat.employeeservice.command.module.entity.EmployeeEntity;
import com.hongphat.employeeservice.command.module.factory.IEmployeeFactory;
import com.hongphat.employeeservice.command.module.repository.IEmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeFactory
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :53 CH 08/01/2025
 */
@Component
public class EmployeeFactory implements IEmployeeFactory {

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
		EmployeeModel model = createFromEvent(eventListener);
		EmployeeEntity entity = toEntity(model);
		toModel(employeeRepository.save(entity));
	}

	@Override
	public EmployeeModel findById(String id) {
		EmployeeEntity entity = employeeRepository.findById(id).orElse(null);
		return toModel(entity);
	}

	@Override
	public List<EmployeeModel> findAll() {
		return employeeRepository.findAll()
				.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	@Override
	public void updateEmployee(String id, EmployeeModel model) {
		employeeRepository.findById(id)
				.map(existingEntity -> update(existingEntity, model))
				.map(employeeRepository::save)
				.map(this::toModel);
	}

	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	}

	private EmployeeModel createFromEvent(CreateEmployeeEvent eventListener) {
		if (eventListener == null) {
			return null;
		}
		return EmployeeModel.builder()
				.id(eventListener.getId())
				.firstName(eventListener.getFirstName())
				.lastName(eventListener.getLastName())
				.kin(eventListener.getKin())
				.isDisciplined(false)
				.build();
	}

	private EmployeeModel toModel(EmployeeEntity entity) {
		if (entity == null) {
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

	private EmployeeEntity toEntity(EmployeeModel model) {
		if (model == null) {
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

	private EmployeeEntity update(EmployeeEntity existingEntity, EmployeeModel model) {
		if (existingEntity == null || model == null) {
			return existingEntity;
		}

		return existingEntity.toBuilder()
				.firstName(model.getFirstName())
				.lastName(model.getLastName())
				.kin(model.getKin())
				.isDisciplined(model.getIsDisciplined())
				.build();
	}
}

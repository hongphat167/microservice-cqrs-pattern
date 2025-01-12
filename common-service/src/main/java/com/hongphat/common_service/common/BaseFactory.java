package com.hongphat.common_service.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BaseFactory
 *
 * @param <E> the type parameter
 * @param <M> the type parameter
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 10 :15 SA 12/01/2025
 */
public abstract class BaseFactory<E, M> {
	/**
	 * To model m.
	 *
	 * @param entity the entity
	 * @return the m
	 */
	public abstract M toModel(E entity);

	/**
	 * To entity e.
	 *
	 * @param model the model
	 * @return the e
	 */
	public abstract E toEntity(M model);


	/**
	 * Update entity với data từ model
	 *
	 * @param existingEntity entity hiện tại cần update
	 * @param model          model chứa data mới
	 * @return entity đã được update
	 */
	protected E updateEntity(E existingEntity, M model) {
		if (isNull(existingEntity) || isNull(model)) {
			return existingEntity;
		}
		// Tạo entity mới với data từ model
		return update(existingEntity, model);
	}

	/**
	 * Merge data từ model vào entity.
	 * Các lớp con có thể override để cung cấp logic merge cụ thể
	 *
	 * @param existingEntity entity hiện tại
	 * @param model          model chứa data mới
	 * @return entity sau khi merge
	 */
	protected abstract E update(E existingEntity, M model);

	/**
	 * Is null boolean.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	// Phương thức protected helper để kiểm tra null
	protected boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * To model list.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	// Các phương thức xử lý collection
	public List<M> toModel(List<E> entities) {
		if (isNull(entities)) {
			return null;
		}
		return entities.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	/**
	 * To entity list.
	 *
	 * @param models the models
	 * @return the list
	 */
	public List<E> toEntity(List<M> models) {
		if (isNull(models)) {
			return null;
		}
		return models.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}

	/**
	 * To model optional optional.
	 *
	 * @param entity the entity
	 * @return the optional
	 */
	// Optional wrapper methods để xử lý null safety
	public Optional<M> toModelOptional(E entity) {
		return Optional.ofNullable(toModel(entity));
	}

	/**
	 * To entity optional optional.
	 *
	 * @param model the model
	 * @return the optional
	 */
	public Optional<E> toEntityOptional(M model) {
		return Optional.ofNullable(toEntity(model));
	}
}

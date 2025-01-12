package com.hongphat.common_service.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BaseFactory with integrated Repository
 *
 * @param <E>  Entity type
 * @param <M>  Model type
 * @param <R>  Repository type
 * @param <ID> the type parameter
 */
public abstract class BaseFactory<
		E extends BaseEntity, M,
		R extends JpaRepository<E, ID>, ID
		> {

	/**
	 * The Repository.
	 */
	protected final R repository;

	/**
	 * Instantiates a new Base factory.
	 *
	 * @param repository the repository
	 */
	protected BaseFactory(R repository) {
		this.repository = repository;
	}

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
	 * Update e.
	 *
	 * @param existingEntity the existing entity
	 * @param model          the model
	 * @return the e
	 */
	protected abstract E update(E existingEntity, M model);

	/**
	 * Is null boolean.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	protected boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * Update entity e.
	 *
	 * @param existingEntity the existing entity
	 * @param model          the model
	 * @return the e
	 */
	protected E updateEntity(E existingEntity, M model) {
		if (isNull(existingEntity) || isNull(model)) {
			return existingEntity;
		}
		return update(existingEntity, model);
	}

	/**
	 * To model list.
	 *
	 * @param entities the entities
	 * @return the list
	 */
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
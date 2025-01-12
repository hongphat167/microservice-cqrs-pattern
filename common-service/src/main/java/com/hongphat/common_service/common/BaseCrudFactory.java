package com.hongphat.common_service.common;

import java.util.List;

/**
 * The type Base crud factory.
 *
 * @param <M> the type parameter
 * @param <E> the type parameter
 */
public interface BaseCrudFactory<M, E> {
	/**
	 * Create and save.
	 *
	 * @param event the event listener
	 */
	void create(E event);

	/**
	 * Find by id m.
	 *
	 * @param id the id
	 * @return the m
	 */
	M get(String id);

	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<M> getList();

	/**
	 * Update book.
	 *
	 * @param id    the id
	 * @param model the model
	 */
	void update(String id, M model);

	/**
	 * Delete by id.
	 *
	 * @param id the id
	 */
	void delete(String id);
}

package com.hongphat.common_service.common;

import org.springframework.data.jpa.domain.Specification;

/**
 * The type Generic specifications.
 */
// Generic Specifications
public class GenericSpecifications {
	/**
	 * Like ignore case specification.
	 *
	 * @param <T>       the type parameter
	 * @param fieldName the field name
	 * @param value     the value
	 * @return the specification
	 */
	public static <T> Specification<T> likeIgnoreCase(String fieldName, String value) {
		return (root, query, cb) -> value == null ? null :
				cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%");
	}
}

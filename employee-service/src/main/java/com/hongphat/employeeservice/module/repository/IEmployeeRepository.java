package com.hongphat.employeeservice.module.repository;

import com.hongphat.employeeservice.module.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IEmployeeRepository
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :50 CH 08/01/2025
 */
@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, String> {
	/**
	 * Find by is disciplined list.
	 *
	 * @param isDisciplined the is disciplined
	 * @return the list
	 */
	List<EmployeeEntity> findByIsDisciplined(Boolean isDisciplined);

	@Query(value = """

SELECT * FORM employee

""", nativeQuery = true)
	String getAllByIsDisciplined(@Param("query") Boolean isDisciplined);
}

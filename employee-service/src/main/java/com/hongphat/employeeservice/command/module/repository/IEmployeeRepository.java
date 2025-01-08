package com.hongphat.employeeservice.command.module.repository;

import com.hongphat.employeeservice.command.module.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * IEmployeeRepository
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8:50 CH 08/01/2025
 */
@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity,String> {
}

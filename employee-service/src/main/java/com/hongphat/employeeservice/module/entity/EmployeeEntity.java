package com.hongphat.employeeservice.module.entity;

import com.hongphat.common_service.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * EmployeeEntity
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 8 :48 CH 08/01/2025
 */
@Entity
@Table(name = "EMPLOYEE")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeEntity extends BaseEntity {

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "KIN")
	private String kin;

	@Column(name = "IS_DISCIPLINED")
	private Boolean isDisciplined;
}

package com.hongphat.employeeservice.command.module.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeEntity {
	@Id
	private String id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "KIN")
	private String kin;

	@Column(name = "IS_DISCIPLINED")
	private Boolean isDisciplined;
}

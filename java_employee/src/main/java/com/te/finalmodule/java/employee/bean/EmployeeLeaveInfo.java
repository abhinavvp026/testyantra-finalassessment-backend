package com.te.finalmodule.java.employee.bean;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLeaveInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sNO;
	private int employeeID;
	private String leaveDate;
	private String leaveStatus;

}
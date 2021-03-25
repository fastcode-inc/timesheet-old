package com.fastcode.timesheetapp1.application.extended.timesheet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateStatus {

	private String status;
	private Long userId;
	private String notes;
}

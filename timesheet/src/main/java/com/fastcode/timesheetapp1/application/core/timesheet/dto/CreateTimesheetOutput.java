package com.fastcode.timesheetapp1.application.core.timesheet.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTimesheetOutput {

    private Long id;
    private String notes;
    private LocalDate periodendingdate;
    private LocalDate periodstartingdate;
	private Long timesheetstatusid;
	private String timesheetstatusDescriptiveField;
	private Long userid;
	private String usersDescriptiveField;

}


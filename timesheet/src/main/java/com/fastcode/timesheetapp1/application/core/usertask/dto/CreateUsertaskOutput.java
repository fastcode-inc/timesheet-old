package com.fastcode.timesheetapp1.application.core.usertask.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUsertaskOutput {

    private Long taskid;
    private Long userid;
	private String taskDescriptiveField;
	private String usersDescriptiveField;

}


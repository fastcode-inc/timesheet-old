package com.fastcode.timesheetapp1.application.core.timesheetstatus.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindTimesheetstatusByIdOutput {

  	private Long id;
  	private String statusname;
	private Long versiono;
 
}


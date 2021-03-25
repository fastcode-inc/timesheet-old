package com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto;

import com.fastcode.timesheetapp1.application.core.timesheetdetails.dto.FindTimesheetdetailsByIdOutput;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimesheetdetailsOutput extends FindTimesheetdetailsByIdOutput{

	private Long customerid;
  	private String customerDescriptiveField;
  	private Long projectid;
  	private String projectDescriptiveField;
}

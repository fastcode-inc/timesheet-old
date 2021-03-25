package com.fastcode.timesheetapp1.application.extended.usertask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsertaskOutput {

	private Long taskid;
  	private Long userid;
  	private String taskDescriptiveField;
  	private String usersDescriptiveField;
  	private Long projectid;
  	private String projectDescriptiveField;
  	private Long customerid;
  	private String customerDescriptiveField;
}

package com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimesheetdetailsInput {

  	private Long id;
  	private Long taskid;
  	private Long timesheetid;
  	private Long timeofftypeid;
  	
  	@NotNull(message = "workdate Should not be null")
  	private LocalDate workdate;
  	private BigDecimal hours;
  	private String notes;
}

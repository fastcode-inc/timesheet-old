package com.fastcode.timesheetapp1.application.core.appconfiguration.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateAppConfigurationInput {

  	@NotNull(message = "id Should not be null")
  	private Long id;
  	
  	@NotNull(message = "type Should not be null")
 	@Length(max = 255, message = "type must be less than 255 characters")
  	private String type;
  	
  	@NotNull(message = "value Should not be null")
 	@Length(max = 255, message = "value must be less than 255 characters")
  	private String value;
  	
  	private Long versiono;
  
}


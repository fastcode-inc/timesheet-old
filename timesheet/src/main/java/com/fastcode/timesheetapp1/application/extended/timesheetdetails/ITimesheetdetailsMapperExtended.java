package com.fastcode.timesheetapp1.application.extended.timesheetdetails;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.fastcode.timesheetapp1.application.core.timesheetdetails.ITimesheetdetailsMapper;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsInput;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsOutput;
import com.fastcode.timesheetapp1.domain.core.timesheetdetails.TimesheetdetailsEntity;

@Mapper(componentModel = "spring")
public interface ITimesheetdetailsMapperExtended extends ITimesheetdetailsMapper {

	TimesheetdetailsEntity timesheetdetailsInputToTimesheetdetailsEntity(TimesheetdetailsInput timesheetdetailsDto);
	
	@Mappings({ 
	    @Mapping(source = "entity.task.id", target = "taskid"),                   
	    @Mapping(source = "entity.task.name", target = "taskDescriptiveField"),                    
	    @Mapping(source = "entity.timeofftype.id", target = "timeofftypeid"),                   
	    @Mapping(source = "entity.timeofftype.typename", target = "timeofftypeDescriptiveField"),                    
	    @Mapping(source = "entity.timesheet.id", target = "timesheetid"),                   
	    @Mapping(source = "entity.timesheet.periodstartingdate", target = "timesheetDescriptiveField"),                    
	   	}) 
	TimesheetdetailsOutput timesheetdetailsEntityToTimesheetdetailsOutput(TimesheetdetailsEntity entity);
	
}


package com.fastcode.timesheetapp1.application.extended.timesheet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.fastcode.timesheetapp1.application.core.timesheet.ITimesheetMapper;
import com.fastcode.timesheetapp1.application.extended.timesheet.dto.TimesheetOutput;
import com.fastcode.timesheetapp1.domain.core.timesheet.TimesheetEntity;

@Mapper(componentModel = "spring")
public interface ITimesheetMapperExtended extends ITimesheetMapper {

   	@Mappings({ 
   	@Mapping(source = "entity.timesheetstatus.id", target = "timesheetstatusid"),                   
   	@Mapping(source = "entity.timesheetstatus.statusname", target = "timesheetstatusDescriptiveField"),                    
   	@Mapping(source = "entity.users.id", target = "userid"),                   
   	@Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	TimesheetOutput timesheetEntityToTimesheetOutput(TimesheetEntity entity);

}


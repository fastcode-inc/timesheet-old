package com.fastcode.timesheetapp1.application.core.timesheet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.timesheetapp1.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.*;
import com.fastcode.timesheetapp1.domain.core.timesheet.TimesheetEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITimesheetMapper {

   TimesheetEntity createTimesheetInputToTimesheetEntity(CreateTimesheetInput timesheetDto);
   
   @Mappings({ 
   @Mapping(source = "entity.timesheetstatus.id", target = "timesheetstatusid"),                   
   @Mapping(source = "entity.timesheetstatus.statusname", target = "timesheetstatusDescriptiveField"),                    
   @Mapping(source = "entity.users.id", target = "userid"),                   
   @Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   }) 
   CreateTimesheetOutput timesheetEntityToCreateTimesheetOutput(TimesheetEntity entity);
   
    TimesheetEntity updateTimesheetInputToTimesheetEntity(UpdateTimesheetInput timesheetDto);
    
    @Mappings({ 
    @Mapping(source = "entity.timesheetstatus.id", target = "timesheetstatusid"),                   
    @Mapping(source = "entity.timesheetstatus.statusname", target = "timesheetstatusDescriptiveField"),                    
    @Mapping(source = "entity.users.id", target = "userid"),                   
    @Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateTimesheetOutput timesheetEntityToUpdateTimesheetOutput(TimesheetEntity entity);

   	@Mappings({ 
   	@Mapping(source = "entity.timesheetstatus.id", target = "timesheetstatusid"),                   
   	@Mapping(source = "entity.timesheetstatus.statusname", target = "timesheetstatusDescriptiveField"),                    
   	@Mapping(source = "entity.users.id", target = "userid"),                   
   	@Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	FindTimesheetByIdOutput timesheetEntityToFindTimesheetByIdOutput(TimesheetEntity entity);


   @Mappings({
   @Mapping(source = "timesheetstatus.id", target = "id"),                  
   @Mapping(source = "foundTimesheet.id", target = "timesheetId"),
   })
   GetTimesheetstatusOutput timesheetstatusEntityToGetTimesheetstatusOutput(TimesheetstatusEntity timesheetstatus, TimesheetEntity foundTimesheet);
   
   @Mappings({
   @Mapping(source = "users.id", target = "id"),                  
   @Mapping(source = "foundTimesheet.id", target = "timesheetId"),
   })
   GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, TimesheetEntity foundTimesheet);
   
}


package com.fastcode.timesheetapp1.application.core.usertask;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.timesheetapp1.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.application.core.usertask.dto.*;
import com.fastcode.timesheetapp1.domain.core.usertask.UsertaskEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUsertaskMapper {

   UsertaskEntity createUsertaskInputToUsertaskEntity(CreateUsertaskInput usertaskDto);
   
   @Mappings({ 
   @Mapping(source = "entity.task.name", target = "taskDescriptiveField"),                    
   @Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   }) 
   CreateUsertaskOutput usertaskEntityToCreateUsertaskOutput(UsertaskEntity entity);
   
    UsertaskEntity updateUsertaskInputToUsertaskEntity(UpdateUsertaskInput usertaskDto);
    
    @Mappings({ 
    @Mapping(source = "entity.task.name", target = "taskDescriptiveField"),                    
    @Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateUsertaskOutput usertaskEntityToUpdateUsertaskOutput(UsertaskEntity entity);

   	@Mappings({ 
   	@Mapping(source = "entity.task.name", target = "taskDescriptiveField"),                    
   	@Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	FindUsertaskByIdOutput usertaskEntityToFindUsertaskByIdOutput(UsertaskEntity entity);


   @Mappings({
   @Mapping(source = "foundUsertask.taskid", target = "usertaskTaskid"),
   @Mapping(source = "foundUsertask.userid", target = "usertaskUserid"),
   })
   GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, UsertaskEntity foundUsertask);
   
   @Mappings({
   @Mapping(source = "foundUsertask.taskid", target = "usertaskTaskid"),
   @Mapping(source = "foundUsertask.userid", target = "usertaskUserid"),
   })
   GetTaskOutput taskEntityToGetTaskOutput(TaskEntity task, UsertaskEntity foundUsertask);
   
}


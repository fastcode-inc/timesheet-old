package com.fastcode.timesheetapp1.application.core.task;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.timesheetapp1.domain.core.project.ProjectEntity;
import com.fastcode.timesheetapp1.application.core.task.dto.*;
import com.fastcode.timesheetapp1.domain.core.task.TaskEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITaskMapper {

   TaskEntity createTaskInputToTaskEntity(CreateTaskInput taskDto);
   
   @Mappings({ 
   @Mapping(source = "entity.project.id", target = "projectid"),                   
   @Mapping(source = "entity.project.name", target = "projectDescriptiveField"),                    
   }) 
   CreateTaskOutput taskEntityToCreateTaskOutput(TaskEntity entity);
   
    TaskEntity updateTaskInputToTaskEntity(UpdateTaskInput taskDto);
    
    @Mappings({ 
    @Mapping(source = "entity.project.id", target = "projectid"),                   
    @Mapping(source = "entity.project.name", target = "projectDescriptiveField"),                    
   	}) 
   	UpdateTaskOutput taskEntityToUpdateTaskOutput(TaskEntity entity);

   	@Mappings({ 
   	@Mapping(source = "entity.project.id", target = "projectid"),                   
   	@Mapping(source = "entity.project.name", target = "projectDescriptiveField"),                    
   	}) 
   	FindTaskByIdOutput taskEntityToFindTaskByIdOutput(TaskEntity entity);


   @Mappings({
   @Mapping(source = "project.description", target = "description"),                  
   @Mapping(source = "project.id", target = "id"),                  
   @Mapping(source = "project.name", target = "name"),                  
   @Mapping(source = "foundTask.id", target = "taskId"),
   })
   GetProjectOutput projectEntityToGetProjectOutput(ProjectEntity project, TaskEntity foundTask);
   
}


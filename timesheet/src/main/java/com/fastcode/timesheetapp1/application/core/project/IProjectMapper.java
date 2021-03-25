package com.fastcode.timesheetapp1.application.core.project;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.timesheetapp1.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp1.application.core.project.dto.*;
import com.fastcode.timesheetapp1.domain.core.project.ProjectEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProjectMapper {

   ProjectEntity createProjectInputToProjectEntity(CreateProjectInput projectDto);
   
   @Mappings({ 
   @Mapping(source = "entity.customer.customerid", target = "customerid"),                   
   @Mapping(source = "entity.customer.name", target = "customerDescriptiveField"),                    
   }) 
   CreateProjectOutput projectEntityToCreateProjectOutput(ProjectEntity entity);
   
    ProjectEntity updateProjectInputToProjectEntity(UpdateProjectInput projectDto);
    
    @Mappings({ 
    @Mapping(source = "entity.customer.customerid", target = "customerid"),                   
    @Mapping(source = "entity.customer.name", target = "customerDescriptiveField"),                    
   	}) 
   	UpdateProjectOutput projectEntityToUpdateProjectOutput(ProjectEntity entity);

   	@Mappings({ 
   	@Mapping(source = "entity.customer.customerid", target = "customerid"),                   
   	@Mapping(source = "entity.customer.name", target = "customerDescriptiveField"),                    
   	}) 
   	FindProjectByIdOutput projectEntityToFindProjectByIdOutput(ProjectEntity entity);


   @Mappings({
   @Mapping(source = "customer.description", target = "description"),                  
   @Mapping(source = "customer.name", target = "name"),                  
   @Mapping(source = "foundProject.id", target = "projectId"),
   })
   GetCustomerOutput customerEntityToGetCustomerOutput(CustomerEntity customer, ProjectEntity foundProject);
   
}


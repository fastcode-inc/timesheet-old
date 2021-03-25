package com.fastcode.timesheetapp1.application.extended.usertask;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.fastcode.timesheetapp1.application.core.usertask.IUsertaskMapper;
import com.fastcode.timesheetapp1.application.extended.usertask.dto.UsertaskOutput;
import com.fastcode.timesheetapp1.domain.core.usertask.UsertaskEntity;

@Mapper(componentModel = "spring")
public interface IUsertaskMapperExtended extends IUsertaskMapper {

	@Mappings({ 
		@Mapping(source = "entity.task.name", target = "taskDescriptiveField"),                    
		@Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
	}) 
	UsertaskOutput usertaskEntityToUsertaskOutput(UsertaskEntity entity);

}



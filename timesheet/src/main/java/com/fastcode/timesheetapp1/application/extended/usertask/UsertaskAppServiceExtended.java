package com.fastcode.timesheetapp1.application.extended.usertask;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fastcode.timesheetapp1.application.core.project.dto.FindProjectByIdOutput;
import com.fastcode.timesheetapp1.application.core.task.dto.FindTaskByIdOutput;
import com.fastcode.timesheetapp1.application.core.usertask.IUsertaskMapper;
import com.fastcode.timesheetapp1.application.core.usertask.UsertaskAppService;
import com.fastcode.timesheetapp1.application.extended.project.IProjectAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.usertask.dto.UsertaskOutput;
import com.fastcode.timesheetapp1.domain.extended.usertask.IUsertaskRepositoryExtended;

import lombok.NonNull;

import com.fastcode.timesheetapp1.domain.extended.task.ITaskRepositoryExtended;
import com.fastcode.timesheetapp1.domain.core.task.ITaskRepository;
import com.fastcode.timesheetapp1.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp1.domain.core.usertask.IUsertaskRepository;
import com.fastcode.timesheetapp1.domain.core.usertask.UsertaskEntity;
import com.fastcode.timesheetapp1.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("usertaskAppServiceExtended")
public class UsertaskAppServiceExtended extends UsertaskAppService implements IUsertaskAppServiceExtended {

	public UsertaskAppServiceExtended(IUsertaskRepositoryExtended usertaskRepositoryExtended, ITaskAppServiceExtended taskAppServiceExtended,
			IProjectAppServiceExtended projectAppServiceExtended,ITaskRepositoryExtended taskRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUsertaskMapperExtended mapper,LoggingHelper logHelper) {

		super(usertaskRepositoryExtended,
		taskRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

		this._usertaskRepositoryExtended = usertaskRepositoryExtended;
		this.mapperExtended = mapper;
		this._taskAppServiceExtended = taskAppServiceExtended;
		this._projectAppServiceExtended = projectAppServiceExtended;
	}

 	//Add your custom code here
	@Qualifier("usertaskRepositoryExtended")
	@NonNull protected final IUsertaskRepositoryExtended _usertaskRepositoryExtended;
	
	@Qualifier("IUsertaskMapperExtendedImpl")
	@NonNull protected final IUsertaskMapperExtended mapperExtended;
	
	@Qualifier("taskAppServiceExtended")
	@NonNull protected final ITaskAppServiceExtended _taskAppServiceExtended;
	
	@Qualifier("projectAppServiceExtended")
	@NonNull protected final IProjectAppServiceExtended _projectAppServiceExtended;
	
	public List<UsertaskOutput> getUsertaskList(Long userId) {
		
		List<UsertaskOutput> list = new ArrayList<UsertaskOutput>();
		List<UsertaskEntity> entityList = _usertaskRepositoryExtended.findByUserid(userId);
		
		for(UsertaskEntity obj : entityList) {
			
			UsertaskOutput output = mapperExtended.usertaskEntityToUsertaskOutput(obj);
			if(output.getTaskid() !=null) {
			 FindTaskByIdOutput task = _taskAppServiceExtended.findById(output.getTaskid());
			 output.setProjectid(task.getProjectid());
			 output.setProjectDescriptiveField(task.getProjectDescriptiveField());
			 
			 FindProjectByIdOutput project = _projectAppServiceExtended.findById(task.getProjectid());
			 output.setCustomerid(project.getCustomerid());
			 output.setCustomerDescriptiveField(project.getCustomerDescriptiveField());
			 
			}
			
			list.add(output);
		}
		return list;
	}
 
}


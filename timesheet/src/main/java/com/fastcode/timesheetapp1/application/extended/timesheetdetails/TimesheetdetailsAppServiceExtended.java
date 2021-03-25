package com.fastcode.timesheetapp1.application.extended.timesheetdetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fastcode.timesheetapp1.application.core.project.dto.FindProjectByIdOutput;
import com.fastcode.timesheetapp1.application.core.task.dto.FindTaskByIdOutput;
import com.fastcode.timesheetapp1.application.core.timesheetdetails.TimesheetdetailsAppService;
import com.fastcode.timesheetapp1.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.project.IProjectAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsInput;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsOutput;
import com.fastcode.timesheetapp1.domain.extended.timesheetdetails.ITimesheetdetailsRepositoryExtended;

import liquibase.pro.license.keymgr.e;
import lombok.NonNull;

import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp1.domain.core.timeofftype.TimeofftypeEntity;
import com.fastcode.timesheetapp1.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp1.domain.core.timesheetdetails.TimesheetdetailsEntity;
import com.fastcode.timesheetapp1.domain.extended.task.ITaskRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.timeofftype.ITimeofftypeRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.timesheet.ITimesheetRepositoryExtended;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("timesheetdetailsAppServiceExtended")
public class TimesheetdetailsAppServiceExtended extends TimesheetdetailsAppService implements ITimesheetdetailsAppServiceExtended {

	public TimesheetdetailsAppServiceExtended(ITimesheetdetailsRepositoryExtended timesheetdetailsRepositoryExtended,ITaskAppServiceExtended taskAppServiceExtended,
			IProjectAppServiceExtended projectAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended, ITaskRepositoryExtended taskRepositoryExtended,
			ITimeofftypeRepositoryExtended timeofftypeRepositoryExtended,ITimesheetRepositoryExtended timesheetRepositoryExtended,ITimesheetdetailsMapperExtended mapper,LoggingHelper logHelper) {

		super(timesheetdetailsRepositoryExtended,
				taskRepositoryExtended,timeofftypeRepositoryExtended,timesheetRepositoryExtended,mapper,logHelper);

		this.extendedMapper = mapper;
		this.timesheetdetailsRepositoryExtended = timesheetdetailsRepositoryExtended;
		this.taskAppServiceExtended = taskAppServiceExtended;
		this.projectAppServiceExtended = projectAppServiceExtended;
		this.usersAppServiceExtended = usersAppServiceExtended;
	}

	//Add your custom code here

	@Qualifier("ITimesheetdetailsMapperExtendedImpl")
	@NonNull protected final ITimesheetdetailsMapperExtended extendedMapper;

	@Qualifier("timesheetdetailsRepositoryExtended")
	@NonNull protected final ITimesheetdetailsRepositoryExtended timesheetdetailsRepositoryExtended;

	@Qualifier("taskAppServiceExtended")
	@NonNull protected final ITaskAppServiceExtended taskAppServiceExtended;

	@Qualifier("projectAppServiceExtended")
	@NonNull protected final IProjectAppServiceExtended projectAppServiceExtended;

	@Qualifier("usersAppServiceExtended")
	@NonNull protected final IUsersAppServiceExtended usersAppServiceExtended;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, String> createMultipleDetails(List<TimesheetdetailsInput> timesheetdetailsList) {

		deleteIfInputNotContainsEntity(timesheetdetailsList);
		for(TimesheetdetailsInput input : timesheetdetailsList) {
			TaskEntity taskEntity = null;
			TimeofftypeEntity timeoffEntity = null;
			TimesheetEntity timesheetEntity = _timesheetRepository.findById(input.getTimesheetid()).orElse(null);
			if(timesheetEntity == null) {
				throw new EntityNotFoundException("Timesheet id is not valid");
			}

			if(input.getTaskid() !=null) {
				taskEntity = _taskRepository.findById(input.getTaskid()).orElse(null);
				if(taskEntity == null) {
					throw new EntityNotFoundException("Task id is not valid");
				}
			}

			if(input.getTimeofftypeid() !=null) {
				timeoffEntity = _timeofftypeRepository.findById(input.getTimeofftypeid()).orElse(null);
				if(timeoffEntity == null) {
					throw new EntityNotFoundException("Timeofftype id is not valid");
				}
			}

			TimesheetdetailsEntity details = extendedMapper.timesheetdetailsInputToTimesheetdetailsEntity(input);
			details.setTask(taskEntity);
			details.setTimeofftype(timeoffEntity);
			details.setTimesheet(timesheetEntity);

			if(details.getId() != null) {
				TimesheetdetailsEntity entity = _timesheetdetailsRepository.findById(details.getId()).orElse(null);
				if(entity !=null) {
					details.setVersiono(entity.getVersiono());
				}
			}
			_timesheetdetailsRepository.save(details);

		}

		Map<String,String> response = new HashMap<String, String>();
		response.put("msg", "Timesheet details Successfully added");

		return response;
	}
	
	public void deleteIfInputNotContainsEntity (List<TimesheetdetailsInput> timesheetdetailsList) {
		
		List<TimesheetdetailsOutput> detailsList = new ArrayList<TimesheetdetailsOutput>();
		
		if(timesheetdetailsList !=null && !timesheetdetailsList.isEmpty()) {
			detailsList = findByWorkDate(timesheetdetailsList.stream().findFirst().get().getWorkdate());
		
			for(TimesheetdetailsOutput output : detailsList) {
				TimesheetdetailsInput input = timesheetdetailsList.stream()
				  .filter(customer -> !output.getId().equals(customer.getId()))
				  .findAny()
				  .orElse(null);
				if(input !=null) {
					delete(output.getId());
				}
			}
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<TimesheetdetailsOutput> findByTimesheetId(Long timesheetId) {

		List<TimesheetdetailsEntity> foundTimesheetdetails = timesheetdetailsRepositoryExtended.findByTimesheetid(timesheetId);
		if (foundTimesheetdetails.isEmpty())  
			return null; 

		return setProjectAndCustomerInfo(foundTimesheetdetails);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<TimesheetdetailsOutput> findByWorkDate(LocalDate workDate) {

		UsersEntity loggedInUser = usersAppServiceExtended.getUsers();
		
		List<TimesheetdetailsEntity> foundTimesheetdetails = timesheetdetailsRepositoryExtended.findByWorkdateAndUserId(workDate, loggedInUser.getId());
		return setProjectAndCustomerInfo(foundTimesheetdetails);
	}

	public List<TimesheetdetailsOutput> setProjectAndCustomerInfo(List<TimesheetdetailsEntity> timesheetdetails) {

		List<TimesheetdetailsOutput> list = new ArrayList<TimesheetdetailsOutput>();

		for(TimesheetdetailsEntity entity : timesheetdetails) {
			TimesheetdetailsOutput output = extendedMapper.timesheetdetailsEntityToTimesheetdetailsOutput(entity);

			if(output.getTaskid() !=null ) {
				FindTaskByIdOutput task = taskAppServiceExtended.findById(output.getTaskid());
				output.setProjectid(task.getProjectid());
				output.setProjectDescriptiveField(task.getProjectDescriptiveField());

				FindProjectByIdOutput project = projectAppServiceExtended.findById(task.getProjectid());
				output.setCustomerid(project.getCustomerid());
				output.setCustomerDescriptiveField(project.getCustomerDescriptiveField());
			}
			list.add(output);
		}
		return list;
	}

}


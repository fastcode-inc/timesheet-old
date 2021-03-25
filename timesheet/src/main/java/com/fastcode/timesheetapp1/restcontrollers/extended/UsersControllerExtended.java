package com.fastcode.timesheetapp1.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.timesheetapp1.restcontrollers.core.UsersController;
import com.fastcode.timesheetapp1.addons.scheduler.application.job.IJobAppService;
import com.fastcode.timesheetapp1.addons.scheduler.application.job.dto.CreateJobInput;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.ITriggerAppService;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.dto.CreateTriggerInput;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.dto.EmailTriggerInfo;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.dto.GetTriggerOutput;
import com.fastcode.timesheetapp1.application.extended.authorization.users.IUsersAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.timesheet.ITimesheetAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.usertask.IUsertaskAppServiceExtended;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fastcode.timesheetapp1.security.JWTAppService;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp1.commons.search.OffsetBasedPageRequest;
import com.fastcode.timesheetapp1.commons.search.SearchCriteria;
import com.fastcode.timesheetapp1.commons.search.SearchUtils;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;

@RestController
@RequestMapping("/users/extended")
public class UsersControllerExtended extends UsersController {

	public UsersControllerExtended(IUsersAppServiceExtended usersAppServiceExtended, ITimesheetAppServiceExtended timesheetAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended, IUsertaskAppServiceExtended usertaskAppServiceExtended,
			PasswordEncoder pEncoder,JWTAppService jwtAppService, LoggingHelper helper, Environment env, IJobAppService jobApp, ITriggerAppService triggerApp) {
		super(
				usersAppServiceExtended,
				timesheetAppServiceExtended,
				userspermissionAppServiceExtended,
				usersroleAppServiceExtended,
				usertaskAppServiceExtended,
				pEncoder,
				jwtAppService,
				helper, env);

		this.jobApp = jobApp;
		this.triggerApp = triggerApp;
		this.usersAppServiceExtended = usersAppServiceExtended;
	}

	@NonNull protected final IJobAppService jobApp;

	@NonNull protected final ITriggerAppService triggerApp;

	@Qualifier("usersAppServiceExtended")
	@NonNull protected final IUsersAppServiceExtended usersAppServiceExtended;

	//Add your custom code here
	
    @PreAuthorize("hasAnyAuthority('USERSENTITY_READ')")
	@RequestMapping(value= "/getEmployees", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity getEmployees(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		if(sort == null || sort.isEmpty()) {
			sort = Sort.by(Sort.Direction.ASC, "firstname");
		}
		
		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		return ResponseEntity.ok(usersAppServiceExtended.findEmployees(search,Pageable));
	}
	
	@PreAuthorize("hasAnyAuthority('SET_REMINDER')")
	@RequestMapping(value= "/getReminderDetails",method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<Map<String,String>> getReminderDetails() throws ClassNotFoundException, Exception {
		UsersEntity loggedInUser = usersAppServiceExtended.getUsers();
		
		GetTriggerOutput trigger = triggerApp.returnTrigger(loggedInUser.getUsername() + "trigger10", "emailTriggerGroup10");
		Map<String,String> output = new HashMap<String, String>();
		try {
			if(trigger != null) {
				String[] cronData = trigger.getCronExpression().split(" ");
				output.put("days", cronData[5]);
				output.put("time", cronData[2] + ":" + cronData[1]);	
			}
		} catch(Exception e) {}
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('SET_REMINDER')")
	@RequestMapping(value= "/reminder",method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<Map<String,String>> createTriggerAndSendEmail(@RequestBody @Valid EmailTriggerInfo info) throws ClassNotFoundException, Exception {

		UsersEntity loggedInUser = usersAppServiceExtended.getUsers();

		if(jobApp.returnJob("emailJob", "emailGroup") == null ) {
			CreateJobInput jobInput = new CreateJobInput();
			jobInput.setIsDurable(true);
			jobInput.setJobClass("com.fastcode.timesheetapp1.addons.scheduler.samplejobs.emailJob");
			jobInput.setJobName("emailJob");
			jobInput.setJobGroup("emailJobGroup");
			jobInput.setJobDescription("Reminder Email Job");

			jobApp.createJob(jobInput);

		}

		if(triggerApp.returnTrigger(loggedInUser.getUsername() + "trigger10", "emailTriggerGroup10") != null) {
			triggerApp.cancelTrigger(loggedInUser.getUsername() + "trigger10", "emailTriggerGroup10");
		}
		CreateTriggerInput triggerInput = new CreateTriggerInput();
		String cronExpression = "0 " + info.getTime().getMinute() + " " + info.getTime().getHour() + " ? * " + info.getDays() +" *";

		// 0 30 4 ? * MON,TUE,WED,THU,FRI *
		triggerInput.setCronExpression(cronExpression);
		triggerInput.setJobName("emailJob");
		triggerInput.setJobGroup("emailJobGroup");
		triggerInput.setJobClass("com.fastcode.timesheetapp1.addons.scheduler.samplejobs.emailJob");
		//triggerInput.setStartTime(date);
		triggerInput.setTriggerName(loggedInUser.getUsername() + "trigger10");
		triggerInput.setTriggerGroup("emailTriggerGroup10");
		triggerInput.setTriggerType("cron");

		Map<String, String> mapData = new HashMap<String, String>();
		mapData.put("email", loggedInUser.getEmailaddress());
		triggerInput.setTriggerMapData(mapData);

		Boolean status = triggerApp.createTrigger(triggerInput);

		Map<String,String> output = new HashMap<String, String>();
		if(status) {
			usersAppServiceExtended.associateTriggerWithUser(loggedInUser, triggerInput);
			output.put("status", "true");
			output.put("msg", "trigger successfully associated");
		}
		else {
			output.put("status", "false");
			output.put("msg", "trigger already exists");
		}
		return new ResponseEntity(output, HttpStatus.OK);
	}	

}


package com.fastcode.timesheetapp1.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.timesheetapp1.restcontrollers.core.UsertaskController;

import lombok.NonNull;

import com.fastcode.timesheetapp1.application.extended.usertask.IUsertaskAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.usertask.dto.UsertaskOutput;
import com.fastcode.timesheetapp1.application.extended.task.ITaskAppServiceExtended;
import com.fastcode.timesheetapp1.application.core.usertask.dto.GetTaskOutput;
import com.fastcode.timesheetapp1.application.extended.authorization.users.IUsersAppServiceExtended;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.domain.core.usertask.UsertaskId;

@RestController
@RequestMapping("/usertask/extended")
public class UsertaskControllerExtended extends UsertaskController {

	public UsertaskControllerExtended(IUsertaskAppServiceExtended usertaskAppServiceExtended, ITaskAppServiceExtended taskAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
			LoggingHelper helper, Environment env) {
		super(
				usertaskAppServiceExtended,
				taskAppServiceExtended,
				usersAppServiceExtended,
				helper, env);
		
		this.usersAppServiceExtended = usersAppServiceExtended;
		this.userstaskAppServiceExtended = usertaskAppServiceExtended;
	}
	
	@Qualifier("usersAppServiceExtended")
	@NonNull protected final IUsersAppServiceExtended usersAppServiceExtended;
	
	@Qualifier("usertaskAppServiceExtended")
	@NonNull protected final IUsertaskAppServiceExtended userstaskAppServiceExtended;
	
	//Add your custom code here
//	@PreAuthorize("hasAnyAuthority('USERTASKENTITY_READ')")
	@RequestMapping(value = "/taskList", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<UsertaskOutput>> getTaskAgainstUser() {
		
		UsersEntity loggedInUser = usersAppServiceExtended.getUsers();
		List<UsertaskOutput> output = userstaskAppServiceExtended.getUsertaskList(loggedInUser.getId());

		return new ResponseEntity(output, HttpStatus.OK);
	}

}


package com.fastcode.timesheetapp1.application.extended.authorization.users;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.dto.CreateTriggerInput;
import com.fastcode.timesheetapp1.application.core.authorization.users.IUsersAppService;
import com.fastcode.timesheetapp1.application.core.authorization.users.dto.FindUsersByIdOutput;
import com.fastcode.timesheetapp1.application.core.authorization.users.dto.UpdateUsersOutput;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;

public interface IUsersAppServiceExtended extends IUsersAppService {

	//Add your custom code here
	public UpdateUsersOutput associateTriggerWithUser(UsersEntity users,CreateTriggerInput trigger);

	public Boolean parseTokenAndCheckIfPermissionExists(String token, String permission);

	public List<FindUsersByIdOutput> findEmployees(String search, Pageable pageable) throws Exception;
	
}

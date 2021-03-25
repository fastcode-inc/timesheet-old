package com.fastcode.timesheetapp1.application.extended.usertask;

import java.util.List;

import com.fastcode.timesheetapp1.application.core.usertask.IUsertaskAppService;
import com.fastcode.timesheetapp1.application.extended.usertask.dto.UsertaskOutput;

public interface IUsertaskAppServiceExtended extends IUsertaskAppService {

	//Add your custom code here
	public List<UsertaskOutput> getUsertaskList(Long userId);
}


package com.fastcode.timesheetapp1.application.extended.timesheetstatus;

import com.fastcode.timesheetapp1.application.core.timesheetstatus.ITimesheetstatusAppService;
import com.fastcode.timesheetapp1.application.core.timesheetstatus.dto.FindTimesheetstatusByIdOutput;

public interface ITimesheetstatusAppServiceExtended extends ITimesheetstatusAppService {

	//Add your custom code here
	public FindTimesheetstatusByIdOutput findByStatusname(String statusname);
}

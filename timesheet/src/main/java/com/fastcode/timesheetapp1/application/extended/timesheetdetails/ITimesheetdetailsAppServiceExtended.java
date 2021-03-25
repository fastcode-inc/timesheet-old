package com.fastcode.timesheetapp1.application.extended.timesheetdetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fastcode.timesheetapp1.application.core.timesheetdetails.ITimesheetdetailsAppService;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsInput;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsOutput;

public interface ITimesheetdetailsAppServiceExtended extends ITimesheetdetailsAppService {

	//Add your custom code here
	public Map<String, String> createMultipleDetails(List<TimesheetdetailsInput> timesheetdetailsList);
		
	public List<TimesheetdetailsOutput> findByTimesheetId(Long timesheetId);
	
	public List<TimesheetdetailsOutput> findByWorkDate(LocalDate workDate);
}

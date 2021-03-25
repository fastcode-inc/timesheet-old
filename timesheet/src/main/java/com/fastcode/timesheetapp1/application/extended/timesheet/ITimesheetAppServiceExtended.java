package com.fastcode.timesheetapp1.application.extended.timesheet;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.fastcode.timesheetapp1.application.core.timesheet.ITimesheetAppService;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.UpdateTimesheetOutput;
import com.fastcode.timesheetapp1.application.extended.timesheet.dto.TimesheetOutput;
import com.fastcode.timesheetapp1.application.extended.timesheet.dto.UpdateStatus;
import com.fastcode.timesheetapp1.commons.search.SearchCriteria;

public interface ITimesheetAppServiceExtended extends ITimesheetAppService {

	//Add your custom code here
	public TimesheetOutput findTimesheetByDate(LocalDate date, Boolean includeDetails, Long userId);

	public UpdateTimesheetOutput updateTimesheetStatus(Long timesheetId, UpdateStatus input);
	
//	public List<TimesheetOutput> findWithHours(SearchCriteria search, Pageable pageable) throws Exception;
}

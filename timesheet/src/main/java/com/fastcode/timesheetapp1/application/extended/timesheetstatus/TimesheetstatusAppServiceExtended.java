package com.fastcode.timesheetapp1.application.extended.timesheetstatus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fastcode.timesheetapp1.application.core.timesheetstatus.TimesheetstatusAppService;
import com.fastcode.timesheetapp1.application.core.timesheetstatus.dto.FindTimesheetstatusByIdOutput;
import com.fastcode.timesheetapp1.domain.core.timesheetstatus.TimesheetstatusEntity;
import com.fastcode.timesheetapp1.domain.extended.timesheetstatus.ITimesheetstatusRepositoryExtended;

import lombok.NonNull;

import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("timesheetstatusAppServiceExtended")
public class TimesheetstatusAppServiceExtended extends TimesheetstatusAppService implements ITimesheetstatusAppServiceExtended {

	public TimesheetstatusAppServiceExtended(ITimesheetstatusRepositoryExtended timesheetstatusRepositoryExtended,
				ITimesheetstatusMapperExtended mapper,LoggingHelper logHelper) {

		super(timesheetstatusRepositoryExtended,
		mapper,logHelper);
		
		this.timesheetstatusRepositoryExtended = timesheetstatusRepositoryExtended;

	}

 	//Add your custom code here
	@Qualifier("timesheetstatusRepositoryExtended")
	@NonNull protected final ITimesheetstatusRepositoryExtended timesheetstatusRepositoryExtended;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindTimesheetstatusByIdOutput findByStatusname(String statusname) {

		TimesheetstatusEntity foundTimesheetstatus = timesheetstatusRepositoryExtended.findByStatusnameIgnoreCase(statusname);
		if (foundTimesheetstatus == null)  
			return null; 
 	   
 	    return mapper.timesheetstatusEntityToFindTimesheetstatusByIdOutput(foundTimesheetstatus);
	}
 
}


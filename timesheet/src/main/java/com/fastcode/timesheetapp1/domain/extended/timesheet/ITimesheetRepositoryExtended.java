package com.fastcode.timesheetapp1.domain.extended.timesheet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.timesheet.ITimesheetRepository;
import com.fastcode.timesheetapp1.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp1.domain.core.timesheetdetails.TimesheetdetailsEntity;

import java.time.LocalDate;
import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("timesheetRepositoryExtended")
public interface ITimesheetRepositoryExtended extends ITimesheetRepository {

	//Add your custom code here
	@Query("select t from TimesheetEntity t where t.periodstartingdate <= ?1 AND t.periodendingdate >= ?1 AND t.users.id=?2")
	TimesheetEntity findByDate(LocalDate date, Long userId);

}


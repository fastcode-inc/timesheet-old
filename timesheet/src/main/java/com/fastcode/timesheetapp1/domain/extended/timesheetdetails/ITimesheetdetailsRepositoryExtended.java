package com.fastcode.timesheetapp1.domain.extended.timesheetdetails;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.timesheetdetails.ITimesheetdetailsRepository;
import com.fastcode.timesheetapp1.domain.core.timesheetdetails.TimesheetdetailsEntity;

import java.time.LocalDate;
import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("timesheetdetailsRepositoryExtended")
public interface ITimesheetdetailsRepositoryExtended extends ITimesheetdetailsRepository {

	//Add your custom code here
	@Query("select t from TimesheetdetailsEntity t where t.timesheet.id=?1")
	List<TimesheetdetailsEntity> findByTimesheetid(Long timesheetId);
	
	@Query("select t from TimesheetdetailsEntity t where t.workdate = ?1 and t.timesheet.users.id=?2")
	List<TimesheetdetailsEntity> findByWorkdateAndUserId(LocalDate workDate, Long userId);
}


package com.fastcode.timesheetapp1.application.core.timesheetstatus;

import org.mapstruct.Mapper;
import com.fastcode.timesheetapp1.application.core.timesheetstatus.dto.*;
import com.fastcode.timesheetapp1.domain.core.timesheetstatus.TimesheetstatusEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITimesheetstatusMapper {

   TimesheetstatusEntity createTimesheetstatusInputToTimesheetstatusEntity(CreateTimesheetstatusInput timesheetstatusDto);
   CreateTimesheetstatusOutput timesheetstatusEntityToCreateTimesheetstatusOutput(TimesheetstatusEntity entity);
   
    TimesheetstatusEntity updateTimesheetstatusInputToTimesheetstatusEntity(UpdateTimesheetstatusInput timesheetstatusDto);
    
   	UpdateTimesheetstatusOutput timesheetstatusEntityToUpdateTimesheetstatusOutput(TimesheetstatusEntity entity);

   	FindTimesheetstatusByIdOutput timesheetstatusEntityToFindTimesheetstatusByIdOutput(TimesheetstatusEntity entity);


}


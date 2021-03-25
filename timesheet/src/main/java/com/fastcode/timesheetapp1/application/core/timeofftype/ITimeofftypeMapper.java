package com.fastcode.timesheetapp1.application.core.timeofftype;

import org.mapstruct.Mapper;
import com.fastcode.timesheetapp1.application.core.timeofftype.dto.*;
import com.fastcode.timesheetapp1.domain.core.timeofftype.TimeofftypeEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface ITimeofftypeMapper {

   TimeofftypeEntity createTimeofftypeInputToTimeofftypeEntity(CreateTimeofftypeInput timeofftypeDto);
   CreateTimeofftypeOutput timeofftypeEntityToCreateTimeofftypeOutput(TimeofftypeEntity entity);
   
    TimeofftypeEntity updateTimeofftypeInputToTimeofftypeEntity(UpdateTimeofftypeInput timeofftypeDto);
    
   	UpdateTimeofftypeOutput timeofftypeEntityToUpdateTimeofftypeOutput(TimeofftypeEntity entity);

   	FindTimeofftypeByIdOutput timeofftypeEntityToFindTimeofftypeByIdOutput(TimeofftypeEntity entity);


}


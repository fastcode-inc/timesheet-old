package com.fastcode.timesheetapp1.application.core.appconfiguration;

import org.mapstruct.Mapper;
import com.fastcode.timesheetapp1.application.core.appconfiguration.dto.*;
import com.fastcode.timesheetapp1.domain.core.appconfiguration.AppConfigurationEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IAppConfigurationMapper {

   AppConfigurationEntity createAppConfigurationInputToAppConfigurationEntity(CreateAppConfigurationInput appconfigurationDto);
   CreateAppConfigurationOutput appConfigurationEntityToCreateAppConfigurationOutput(AppConfigurationEntity entity);
   
    AppConfigurationEntity updateAppConfigurationInputToAppConfigurationEntity(UpdateAppConfigurationInput appConfigurationDto);
    
   	UpdateAppConfigurationOutput appConfigurationEntityToUpdateAppConfigurationOutput(AppConfigurationEntity entity);

   	FindAppConfigurationByIdOutput appConfigurationEntityToFindAppConfigurationByIdOutput(AppConfigurationEntity entity);


}


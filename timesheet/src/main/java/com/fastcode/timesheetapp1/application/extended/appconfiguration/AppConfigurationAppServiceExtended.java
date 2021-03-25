package com.fastcode.timesheetapp1.application.extended.appconfiguration;

import org.springframework.stereotype.Service;
import com.fastcode.timesheetapp1.application.core.appconfiguration.AppConfigurationAppService;

import com.fastcode.timesheetapp1.domain.extended.appconfiguration.IAppConfigurationRepositoryExtended;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("appConfigurationAppServiceExtended")
public class AppConfigurationAppServiceExtended extends AppConfigurationAppService implements IAppConfigurationAppServiceExtended {

	public AppConfigurationAppServiceExtended(IAppConfigurationRepositoryExtended appConfigurationRepositoryExtended,
				IAppConfigurationMapperExtended mapper,LoggingHelper logHelper) {

		super(appConfigurationRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}


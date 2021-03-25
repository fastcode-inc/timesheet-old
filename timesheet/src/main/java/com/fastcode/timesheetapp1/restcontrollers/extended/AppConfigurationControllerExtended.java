package com.fastcode.timesheetapp1.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.timesheetapp1.restcontrollers.core.AppConfigurationController;
import com.fastcode.timesheetapp1.application.extended.appconfiguration.IAppConfigurationAppServiceExtended;
import org.springframework.core.env.Environment;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/appConfiguration/extended")
public class AppConfigurationControllerExtended extends AppConfigurationController {

		public AppConfigurationControllerExtended(IAppConfigurationAppServiceExtended appConfigurationAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		appConfigurationAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}


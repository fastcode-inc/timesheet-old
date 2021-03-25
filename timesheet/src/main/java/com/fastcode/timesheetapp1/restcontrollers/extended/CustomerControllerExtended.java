package com.fastcode.timesheetapp1.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.timesheetapp1.restcontrollers.core.CustomerController;
import com.fastcode.timesheetapp1.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.project.IProjectAppServiceExtended;
import org.springframework.core.env.Environment;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/customer/extended")
public class CustomerControllerExtended extends CustomerController {

		public CustomerControllerExtended(ICustomerAppServiceExtended customerAppServiceExtended, IProjectAppServiceExtended projectAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		customerAppServiceExtended,
    	projectAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}


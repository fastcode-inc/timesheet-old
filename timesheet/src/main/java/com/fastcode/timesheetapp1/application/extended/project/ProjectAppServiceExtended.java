package com.fastcode.timesheetapp1.application.extended.project;

import org.springframework.stereotype.Service;
import com.fastcode.timesheetapp1.application.core.project.ProjectAppService;

import com.fastcode.timesheetapp1.domain.extended.project.IProjectRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("projectAppServiceExtended")
public class ProjectAppServiceExtended extends ProjectAppService implements IProjectAppServiceExtended {

	public ProjectAppServiceExtended(IProjectRepositoryExtended projectRepositoryExtended,
				ICustomerRepositoryExtended customerRepositoryExtended,IProjectMapperExtended mapper,LoggingHelper logHelper) {

		super(projectRepositoryExtended,
		customerRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}


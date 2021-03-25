package com.fastcode.timesheetapp1.application.extended.authorization.rolepermission;

import org.springframework.stereotype.Service;
import com.fastcode.timesheetapp1.application.core.authorization.rolepermission.RolepermissionAppService;

import com.fastcode.timesheetapp1.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.fastcode.timesheetapp1.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.fastcode.timesheetapp1.security.JWTAppService;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended extends RolepermissionAppService implements IRolepermissionAppServiceExtended {

	public RolepermissionAppServiceExtended(JWTAppService jwtAppService,IUsersroleRepositoryExtended usersroleRepositoryExtended,IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IRoleRepositoryExtended roleRepositoryExtended,IRolepermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(jwtAppService, usersroleRepositoryExtended,rolepermissionRepositoryExtended,
		permissionRepositoryExtended,roleRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}


package com.fastcode.timesheetapp1.domain.extended.authorization.usersrole;

import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.authorization.usersrole.IUsersroleRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("usersroleRepositoryExtended")
public interface IUsersroleRepositoryExtended extends IUsersroleRepository {

	//Add your custom code here
}


package com.fastcode.timesheetapp1.domain.extended.authorization.role;

import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.authorization.role.IRoleRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("roleRepositoryExtended")
public interface IRoleRepositoryExtended extends IRoleRepository {

	//Add your custom code here
}


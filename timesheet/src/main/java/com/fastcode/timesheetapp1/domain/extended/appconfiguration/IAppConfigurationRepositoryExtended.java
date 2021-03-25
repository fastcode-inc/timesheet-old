package com.fastcode.timesheetapp1.domain.extended.appconfiguration;

import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.appconfiguration.IAppConfigurationRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("appConfigurationRepositoryExtended")
public interface IAppConfigurationRepositoryExtended extends IAppConfigurationRepository {

	//Add your custom code here
}


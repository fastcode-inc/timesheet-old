package com.fastcode.timesheetapp1.domain.extended.usertask;

import org.springframework.stereotype.Repository;
import com.fastcode.timesheetapp1.domain.core.usertask.IUsertaskRepository;
import com.fastcode.timesheetapp1.domain.core.usertask.UsertaskEntity;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("usertaskRepositoryExtended")
public interface IUsertaskRepositoryExtended extends IUsertaskRepository {

	//Add your custom code here
	List<UsertaskEntity> findByUserid(Long userId);
}


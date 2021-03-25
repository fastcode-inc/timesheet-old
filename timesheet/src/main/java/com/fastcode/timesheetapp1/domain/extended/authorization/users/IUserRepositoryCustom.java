package com.fastcode.timesheetapp1.domain.extended.authorization.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fastcode.timesheetapp1.application.core.authorization.users.dto.FindUsersByIdOutput;

public interface IUserRepositoryCustom {
	Page<FindUsersByIdOutput> findEmployees(String search, Pageable pageable) throws Exception;
}

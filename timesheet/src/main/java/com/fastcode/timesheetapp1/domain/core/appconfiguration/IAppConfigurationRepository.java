package com.fastcode.timesheetapp1.domain.core.appconfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("appConfigurationRepository")
public interface IAppConfigurationRepository extends JpaRepository<AppConfigurationEntity, Long>,QuerydslPredicateExecutor<AppConfigurationEntity> {

}


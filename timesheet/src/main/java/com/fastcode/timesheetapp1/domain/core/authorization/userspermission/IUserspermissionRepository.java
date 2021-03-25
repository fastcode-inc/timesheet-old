package com.fastcode.timesheetapp1.domain.core.authorization.userspermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("userspermissionRepository")
public interface IUserspermissionRepository extends JpaRepository<UserspermissionEntity, UserspermissionId>,QuerydslPredicateExecutor<UserspermissionEntity> {

    List<UserspermissionEntity> findByUsersId(Long usersId);

}


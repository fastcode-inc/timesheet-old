package com.fastcode.timesheetapp1.domain.core.authorization.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("permissionRepository")
public interface IPermissionRepository extends JpaRepository<PermissionEntity, Long>,QuerydslPredicateExecutor<PermissionEntity> {

	@Query("select u from PermissionEntity u where u.name = ?1")
    PermissionEntity findByPermissionName(String value);
    
}


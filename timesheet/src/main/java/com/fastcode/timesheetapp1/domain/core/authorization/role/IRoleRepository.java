package com.fastcode.timesheetapp1.domain.core.authorization.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("roleRepository")
public interface IRoleRepository extends JpaRepository<RoleEntity, Long>,QuerydslPredicateExecutor<RoleEntity> {

	@Query("select u from RoleEntity u where u.name = ?1")
    RoleEntity findByRoleName(String value);
    
}


package com.fastcode.timesheetapp1.domain.core.authorization.usersrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("usersroleRepository")
public interface IUsersroleRepository extends JpaRepository<UsersroleEntity, UsersroleId>,QuerydslPredicateExecutor<UsersroleEntity> {

    List<UsersroleEntity> findByUsersId(Long usersId);

    List<UsersroleEntity> findByRoleId(Long roleId);
}


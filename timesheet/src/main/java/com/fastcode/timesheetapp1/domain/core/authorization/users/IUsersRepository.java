package com.fastcode.timesheetapp1.domain.core.authorization.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("usersRepository")
public interface IUsersRepository extends JpaRepository<UsersEntity, Long>,QuerydslPredicateExecutor<UsersEntity> {

    @Query("select u from UsersEntity u where u.username = ?1")
    UsersEntity findByUsername(String value);  

    UsersEntity findByEmailaddress(String emailAddress);
    
}


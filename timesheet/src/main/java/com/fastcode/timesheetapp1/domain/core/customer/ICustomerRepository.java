package com.fastcode.timesheetapp1.domain.core.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
@Repository("customerRepository")
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long>,QuerydslPredicateExecutor<CustomerEntity> {

}


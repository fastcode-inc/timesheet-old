package com.fastcode.timesheetapp1.addons.reporting.domain.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.time.*;

import java.util.List;

@Repository("dashboardRepository")
public interface IDashboardRepository extends JpaRepository<DashboardEntity, Long>,
        QuerydslPredicateExecutor<DashboardEntity>, IDashboardRepositoryCustom {

	@Query("select r from DashboardEntity r where r.id = ?1 and r.users.id=?2 ")
	DashboardEntity findByDashboardIdAndUsersId(Long dashboardId, Long userId);

	@Query("select r from DashboardEntity r where r.users.id=?1 ")
	List<DashboardEntity> findByUsersId(Long id );
}


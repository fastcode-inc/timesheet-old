package com.fastcode.timesheetapp1.addons.reporting.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.time.*;
import java.util.List;


@Repository("reportRepository")
public interface IReportRepository extends JpaRepository<ReportEntity, Long> , QuerydslPredicateExecutor<ReportEntity> , IReportRepositoryCustom {

	@Query("select r from ReportEntity r where r.id = ?1 and r.users.id=?2 ")
	ReportEntity findByReportIdAndUsersId(Long reportId, Long userId);
    
	@Query("select r from ReportEntity r where r.users.id=?1")
	List<ReportEntity> findByUsersId(Long id );
}


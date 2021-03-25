package com.fastcode.timesheetapp1.addons.reporting.domain.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.*;
import com.fastcode.timesheetapp1.addons.reporting.application.report.dto.ReportDetailsOutput;

public interface IReportRepositoryCustom {
	
	Page<ReportDetailsOutput> getAllReportsByUsersId(Long userId, String search, Pageable pageable) throws Exception;

}


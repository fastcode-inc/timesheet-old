package com.fastcode.timesheetapp1.addons.reporting.domain.reportversion;

import com.fastcode.timesheetapp1.addons.reporting.domain.report.ReportEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import javax.persistence.*;

import org.json.simple.JSONObject;
import com.fastcode.timesheetapp1.addons.reporting.JSONObjectConverter;

import java.time.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "reportversion")
@IdClass(ReportversionId.class)
public class ReportversionEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
  	@EqualsAndHashCode.Include()
  	@Column(name = "user_id", nullable = false)
  	private Long userId; 
	
	@Id
	@EqualsAndHashCode.Include()
  	@Column(name = "report_id", nullable = false)
	private Long reportId;
	
	@Basic
  	@Column(name = "ctype", nullable = true, length =255)
  	private String ctype;
	
	@Basic
  	@Column(name = "description", nullable = true, length =255)
  	private String description;

  	@Column(columnDefinition = "TEXT",name = "query", nullable = true, length =255)
  	@Convert(converter= JSONObjectConverter.class)
  	private JSONObject query;
	
	@Basic
  	@Column(name = "report_type", nullable = true, length =255)
  	private String reportType;
	
	@Basic
  	@Column(name = "title", nullable = false, length =255)
  	private String title;
  	
  	@Basic
	@Column(name = "is_refreshed", nullable = true)
	private Boolean isRefreshed;
  	
  	@Id
  	@Column(name = "report_version", nullable = false, length =255)
  	private String reportVersion;
  	

  	@ManyToOne
  	@JoinColumn(name = "report_id", referencedColumnName = "id", insertable = false, updatable = false)
  	private ReportEntity report;
  	
	@ManyToOne
	@JoinColumn(name = "user_id",referencedColumnName="id", insertable=false, updatable=false)
	private UsersEntity users;

}


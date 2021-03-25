package com.fastcode.timesheetapp1.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.timesheetapp1.restcontrollers.core.TimesheetController;
import com.fastcode.timesheetapp1.security.SecurityConstants;

import lombok.NonNull;

import com.fastcode.timesheetapp1.application.extended.timesheet.ITimesheetAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.timesheet.dto.TimesheetOutput;
import com.fastcode.timesheetapp1.application.extended.timesheet.dto.UpdateStatus;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.ITimesheetdetailsAppServiceExtended;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsInput;
import com.fastcode.timesheetapp1.application.extended.timesheetdetails.dto.TimesheetdetailsOutput;
import com.fastcode.timesheetapp1.application.extended.timesheetstatus.ITimesheetstatusAppServiceExtended;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.CreateTimesheetInput;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.CreateTimesheetOutput;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.FindTimesheetByIdOutput;
import com.fastcode.timesheetapp1.application.core.timesheet.dto.UpdateTimesheetOutput;
import com.fastcode.timesheetapp1.application.extended.authorization.users.IUsersAppServiceExtended;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp1.commons.search.OffsetBasedPageRequest;
import com.fastcode.timesheetapp1.commons.search.SearchCriteria;
import com.fastcode.timesheetapp1.commons.search.SearchUtils;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;

@RestController
@RequestMapping("/timesheet/extended")
public class TimesheetControllerExtended extends TimesheetController {

	public TimesheetControllerExtended(ITimesheetAppServiceExtended timesheetAppServiceExtended, ITimesheetdetailsAppServiceExtended timesheetdetailsAppServiceExtended,
			ITimesheetstatusAppServiceExtended timesheetstatusAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
			LoggingHelper helper, Environment env) {
		super(
				timesheetAppServiceExtended,
				timesheetdetailsAppServiceExtended,
				timesheetstatusAppServiceExtended,
				usersAppServiceExtended,
				helper, env);
		this._timesheetdetailsAppServiceExtended = timesheetdetailsAppServiceExtended;
		this._timesheetAppServiceExtended = timesheetAppServiceExtended;
		this.usersAppServiceExtended = usersAppServiceExtended;
	}

	//Add your custom code here

	@Qualifier("timesheetdetailsExtendedAppService")
	@NonNull  protected final ITimesheetdetailsAppServiceExtended  _timesheetdetailsAppServiceExtended;

	@Qualifier("timesheetExtendedAppService")
	@NonNull  protected final ITimesheetAppServiceExtended  _timesheetAppServiceExtended;

	@Qualifier("usersAppServiceExtended")
	@NonNull protected final IUsersAppServiceExtended usersAppServiceExtended;

	@Override
	@PreAuthorize("hasAnyAuthority('FILL_TIMESHEET')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateTimesheetOutput> create(@RequestBody @Valid CreateTimesheetInput timesheet) {
		CreateTimesheetOutput output=_timesheetAppService.create(timesheet);
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
		return new ResponseEntity(output, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('FILL_TIMESHEET')")
	@RequestMapping(value="/timesheetdetails", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<Map<String,String>> createMultipleDetails(@RequestBody @Valid List<TimesheetdetailsInput> inputList) {

		return new ResponseEntity(_timesheetdetailsAppServiceExtended.createMultipleDetails(inputList), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyAuthority('FILL_TIMESHEET')")
	@RequestMapping(value = "/timesheetdetails", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<TimesheetdetailsOutput>> findTimesheetdetailsByWorkDate(@RequestParam("workDate") String workDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(workDate,formatter);
		List<TimesheetdetailsOutput> output = _timesheetdetailsAppServiceExtended.findByWorkDate(date);

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTimesheet", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<TimesheetOutput> findTimesheetByDate(@RequestParam("date") String date, @RequestParam("includeDetails") Boolean includeDetails, @RequestParam(value="userId", required=false) Long userId,HttpServletRequest request) throws Exception {

		String token = request.getHeader("Authorization");
		if(token !=null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		}

		if(userId !=null) {
			if(!usersAppServiceExtended.parseTokenAndCheckIfPermissionExists(token, "TIMESHEETENTITY_READ")) {
				throw new Exception("You don't have permission to fetch timesheet details against userid " + userId);
			}
		}
		else {
			if(!usersAppServiceExtended.parseTokenAndCheckIfPermissionExists(token, "FILL_TIMESHEET")) {
				throw new Exception("You don't have permission to fetch timesheet details. " + userId);
			}
			UsersEntity loggedInUser = usersAppServiceExtended.getUsers();
			userId = loggedInUser.getId();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate datel = LocalDate.parse(date,formatter);
		TimesheetOutput output = _timesheetAppServiceExtended.findTimesheetByDate(datel, includeDetails, userId);
		Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

		return new ResponseEntity(output, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/updateTimesheetStatus", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateTimesheetOutput> updateTimesheetStatus(@PathVariable Long id, @RequestBody UpdateStatus input,HttpServletRequest request) throws Exception {

		FindTimesheetByIdOutput timesheet = _timesheetAppService.findById(id);
		Optional.ofNullable(timesheet).orElseThrow(() -> new EntityNotFoundException(String.format("Timesheet not found with id " + id)));
		String token = request.getHeader("Authorization");
		if(token !=null && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
		}
		if(input.getUserId() !=null) {

			if(!usersAppServiceExtended.parseTokenAndCheckIfPermissionExists(token, "TIMESHEETSTATUSENTITY_UPDATE")) {
				throw new Exception("You don't have permission to fetch timesheet details against userid " + input.getUserId());
			}

			if(!input.getStatus().equalsIgnoreCase("Rejected") && !input.getStatus().equalsIgnoreCase("Approved")) {
				throw new InvalidInputException("Status value is not valid");
			}
		}
		else
		{
			if(!usersAppServiceExtended.parseTokenAndCheckIfPermissionExists(token, "FILL_TIMESHEET")) {
				throw new Exception("You don't have permission to submit timesheet.");
			}
			UsersEntity loggedInUser = usersAppServiceExtended.getUsers();
			input.setUserId(loggedInUser.getId());
			if(!input.getStatus().equalsIgnoreCase("Submitted")) {
				throw new InvalidInputException("Status value is not valid");
			}
			input.setNotes(timesheet.getNotes());
		}

		UpdateTimesheetOutput output = _timesheetAppServiceExtended.updateTimesheetStatus(id, input);
		if(output == null) {
			throw new InvalidInputException("Status is not open or rejected, can't submit");
		}

		return new ResponseEntity(output, HttpStatus.OK);
	}

//    @PreAuthorize("hasAnyAuthority('TIMESHEETENTITY_READ')")
//	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
//	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {
//
//		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
//		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }
//
//		if(sort == null || sort.isEmpty()) {
//			sort = Sort.by(Sort.Direction.ASC, "periodstartingdate");
//		}
//		
//		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
//		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
//
//		return ResponseEntity.ok(_timesheetAppServiceExtended.findWithHours(searchCriteria,Pageable));
//	}
}


package com.fastcode.timesheetapp1.application.extended.authorization.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fastcode.timesheetapp1.addons.reporting.domain.dashboardversion.IDashboardversionRepository;
import com.fastcode.timesheetapp1.addons.reporting.domain.dashboardversionreport.IDashboardversionreportRepository;
import com.fastcode.timesheetapp1.addons.reporting.domain.reportversion.IReportversionRepository;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.ITriggerAppService;
import com.fastcode.timesheetapp1.addons.scheduler.application.trigger.dto.CreateTriggerInput;
import com.fastcode.timesheetapp1.application.core.authorization.users.UsersAppService;
import com.fastcode.timesheetapp1.application.core.authorization.users.dto.FindUsersByIdOutput;
import com.fastcode.timesheetapp1.application.core.authorization.users.dto.UpdateUsersOutput;
import com.fastcode.timesheetapp1.domain.extended.authorization.users.IUserRepositoryCustom;
import com.fastcode.timesheetapp1.domain.extended.authorization.users.IUsersRepositoryCustomImpl;
import com.fastcode.timesheetapp1.domain.extended.authorization.users.IUsersRepositoryExtended;

import lombok.NonNull;

import com.fastcode.timesheetapp1.domain.core.authorization.users.IUsersRepository;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.userspreference.IUserspreferenceRepository;
import com.fastcode.timesheetapp1.domain.core.authorization.userspreference.UserspreferenceEntity;
import com.fastcode.timesheetapp1.commons.logging.LoggingHelper;
import com.fastcode.timesheetapp1.commons.search.SearchCriteria;

@Service("usersAppServiceExtended")
public class UsersAppServiceExtended extends UsersAppService implements IUsersAppServiceExtended {
	
	@Qualifier("usersRepositoryExtended")
	@NonNull protected final IUserRepositoryCustom usersRepositoryCustom;

	@NonNull protected final ITriggerAppService triggerApp;
	
	public UsersAppServiceExtended(IDashboardversionRepository _dashboardversionRepository, IReportversionRepository _reportversionRepository, IDashboardversionreportRepository _reportDashboardRepository, IUsersRepositoryExtended usersRepositoryExtended,
			IUserspreferenceRepository userspreferenceRepository,IUsersMapperExtended mapper,LoggingHelper logHelper, ITriggerAppService triggerApp, IUserRepositoryCustom usersRepositoryCustom) {

		super(_dashboardversionRepository, _reportversionRepository, _reportDashboardRepository, usersRepositoryExtended,
				userspreferenceRepository, mapper, logHelper);
		this.usersRepositoryCustom = usersRepositoryCustom;
		
		this.triggerApp = triggerApp;
	}

	//Add your custom code here
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateUsersOutput associateTriggerWithUser(UsersEntity users,CreateTriggerInput trigger) {

		users.setTriggerGroup(trigger.getTriggerGroup());
		users.setTriggerName(trigger.getTriggerName());

		//		UsersEntity existing = _usersRepository.findById(usersId).get();
		//
		//		UsersEntity users = mapper.updateUsersInputToUsersEntity(input);
		//		users.setTimesheetsSet(existing.getTimesheetsSet());
		//		users.setTokenverificationsSet(existing.getTokenverificationsSet());
		//		users.setUserspermissionsSet(existing.getUserspermissionsSet());
		//		users.setUsersrolesSet(existing.getUsersrolesSet());
		//		users.setUsertasksSet(existing.getUsertasksSet());

		UsersEntity updatedUsers = _usersRepository.save(users);
		return mapper.usersEntityToUpdateUsersOutput(updatedUsers);
	}

	@Override
	public void delete(Long usersId) {

		UsersEntity existing = _usersRepository.findById(usersId).orElse(null); 

		UserspreferenceEntity userspreference = _userspreferenceRepository.findById(usersId).orElse(null);
		_userspreferenceRepository.delete(userspreference);
		
		try {
			triggerApp.cancelTrigger(existing.getTriggerName(), existing.getTriggerGroup());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		_usersRepository.delete(existing);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean parseTokenAndCheckIfPermissionExists(String token, String permission)
	{
    	SecurityContext securityContext = SecurityContextHolder.getContext();
    	Authentication auth = securityContext.getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority(permission));

	}
	
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUsersByIdOutput> findEmployees(String search, Pageable pageable) throws Exception  {
    	
    	Page<FindUsersByIdOutput> foundUsers = usersRepositoryCustom.findEmployees(search, pageable);
    	return foundUsers.getContent();
    }

}


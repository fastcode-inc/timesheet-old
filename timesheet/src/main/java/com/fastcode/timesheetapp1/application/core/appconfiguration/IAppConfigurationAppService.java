package com.fastcode.timesheetapp1.application.core.appconfiguration;

import org.springframework.data.domain.Pageable;
import com.fastcode.timesheetapp1.application.core.appconfiguration.dto.*;
import com.fastcode.timesheetapp1.commons.search.SearchCriteria;

import java.util.*;

public interface IAppConfigurationAppService {
	
	//CRUD Operations
	
	CreateAppConfigurationOutput create(CreateAppConfigurationInput appconfiguration);

    void delete(Long id);

    UpdateAppConfigurationOutput update(Long id, UpdateAppConfigurationInput input);

    FindAppConfigurationByIdOutput findById(Long id);

    List<FindAppConfigurationByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    //Join Column Parsers
}


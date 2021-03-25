package com.fastcode.timesheetapp1.domain.extended.authorization.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.fastcode.timesheetapp1.application.core.authorization.users.dto.FindUsersByIdOutput;

@Repository("usersRepositoryCustomImpl")
@SuppressWarnings({"unchecked"})
public class IUsersRepositoryCustomImpl implements IUserRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired 
	private Environment env;

	public Page<FindUsersByIdOutput> findEmployees(String search, Pageable pageable) throws Exception {
		String schema = env.getProperty("spring.jpa.properties.hibernate.default_schema");
		
		String qlString = "" 
				+ " SELECT "
				+ " u.id,"
				+ " u.emailaddress,"
				+ " u.firstname,"
				+ " u.lastname,"
				+ " u.username"
   				+ " FROM " + schema + ".users u where (u.isactive = true and (:search is null OR u.username like :search)) "
   				+ " and "
   				+ " u.id in ( "
   				+ "		select users_id from timesheet.usersrole ur"
   				+ "		where ur.role_id in ("
   				+ "			select id from timesheet.role where role.name = 'ROLE_Employee'"
   				+ "		)"
   				+ " ) " ;
		Query query = 
				entityManager.createNativeQuery(qlString)
				.setParameter("search","%" + search + "%")
				.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<Object[]> results = query.getResultList();
		List<FindUsersByIdOutput> finalResults = new ArrayList<>();
		
		for(Object[] obj : results) {
			FindUsersByIdOutput user = new FindUsersByIdOutput();

			// Here you manually obtain value from object and map to your pojo setters
			user.setId(obj[0] !=null ? Long.parseLong(obj[0].toString()) : null);
			user.setEmailaddress(obj[1] !=null ? (obj[1].toString()) : null);
			user.setFirstname(obj[1] !=null ? (obj[2].toString()) : null);
			user.setLastname(obj[1] !=null ? (obj[3].toString()) : null);
			user.setUsername(obj[1] !=null ? (obj[4].toString()) : null);
			
			finalResults.add(user);

		}
		
		int totalRows = results.size();
		Page<FindUsersByIdOutput> result = new PageImpl<FindUsersByIdOutput>(finalResults, pageable, totalRows);

		return result;
	}

}

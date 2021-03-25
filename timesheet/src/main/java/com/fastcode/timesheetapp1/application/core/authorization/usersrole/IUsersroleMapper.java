package com.fastcode.timesheetapp1.application.core.authorization.usersrole;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.timesheetapp1.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.application.core.authorization.usersrole.dto.*;
import com.fastcode.timesheetapp1.domain.core.authorization.usersrole.UsersroleEntity;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUsersroleMapper {

   UsersroleEntity createUsersroleInputToUsersroleEntity(CreateUsersroleInput usersroleDto);
   
   @Mappings({ 
   @Mapping(source = "users.id", target = "usersId"),  
   @Mapping(source = "users.username", target = "usersDescriptiveField"),
   @Mapping(source = "role.displayName", target = "roleDescriptiveField"),                   
   @Mapping(source = "role.id", target = "roleId")                   
   }) 
   CreateUsersroleOutput usersroleEntityToCreateUsersroleOutput(UsersroleEntity entity);
   
    UsersroleEntity updateUsersroleInputToUsersroleEntity(UpdateUsersroleInput usersroleDto);
    
    @Mappings({ 
    @Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
    @Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateUsersroleOutput usersroleEntityToUpdateUsersroleOutput(UsersroleEntity entity);

   	@Mappings({ 
   	@Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
   	@Mapping(source = "entity.users.lastname", target = "usersDescriptiveField"),                    
   	}) 
   	FindUsersroleByIdOutput usersroleEntityToFindUsersroleByIdOutput(UsersroleEntity entity);


   @Mappings({
   @Mapping(source = "foundUsersrole.roleId", target = "usersroleRoleId"),
   @Mapping(source = "foundUsersrole.usersId", target = "usersroleUsersId"),
   })
   GetRoleOutput roleEntityToGetRoleOutput(RoleEntity role, UsersroleEntity foundUsersrole);
   
   @Mappings({
   @Mapping(source = "foundUsersrole.roleId", target = "usersroleRoleId"),
   @Mapping(source = "foundUsersrole.usersId", target = "usersroleUsersId"),
   })
   GetUsersOutput usersEntityToGetUsersOutput(UsersEntity users, UsersroleEntity foundUsersrole);
   
}


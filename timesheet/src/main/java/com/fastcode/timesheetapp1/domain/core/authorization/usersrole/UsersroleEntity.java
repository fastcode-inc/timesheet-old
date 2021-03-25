package com.fastcode.timesheetapp1.domain.core.authorization.usersrole;

import javax.persistence.*;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.users.UsersEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usersrole")
@IdClass(UsersroleId.class)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UsersroleEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include()
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    
    @Id
    @EqualsAndHashCode.Include()
    @Column(name = "users_id", nullable = false)
    private Long usersId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    private RoleEntity role;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "users_id", insertable=false, updatable=false)
    private UsersEntity users;


}




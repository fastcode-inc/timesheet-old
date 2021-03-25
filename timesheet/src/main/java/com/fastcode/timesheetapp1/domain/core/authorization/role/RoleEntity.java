package com.fastcode.timesheetapp1.domain.core.authorization.role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.authorization.rolepermission.RolepermissionEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.usersrole.UsersroleEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.ShallowReference;

@Entity
@Table(name = "role")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class RoleEntity extends AbstractEntity {

    @Basic
    @Column(name = "display_name", nullable = false,length =255)
    private String displayName;

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "name", nullable = false,length =255)
    private String name;

	@ShallowReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolepermissionEntity> rolepermissionsSet = new HashSet<RolepermissionEntity>();
    
    public void addRolepermissions(RolepermissionEntity rolepermissions) {        
    	rolepermissionsSet.add(rolepermissions);
        rolepermissions.setRole(this);
    }
    public void removeRolepermissions(RolepermissionEntity rolepermissions) {
        rolepermissionsSet.remove(rolepermissions);
        rolepermissions.setRole(null);
    }
    
	@ShallowReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsersroleEntity> usersrolesSet = new HashSet<UsersroleEntity>();
    
    public void addUsersroles(UsersroleEntity usersroles) {        
    	usersrolesSet.add(usersroles);
        usersroles.setRole(this);
    }
    public void removeUsersroles(UsersroleEntity usersroles) {
        usersrolesSet.remove(usersroles);
        usersroles.setRole(null);
    }
    

}




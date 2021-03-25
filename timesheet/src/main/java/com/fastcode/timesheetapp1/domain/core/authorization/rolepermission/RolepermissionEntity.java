package com.fastcode.timesheetapp1.domain.core.authorization.rolepermission;

import javax.persistence.*;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.timesheetapp1.domain.core.authorization.role.RoleEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rolepermission")
@IdClass(RolepermissionId.class)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class RolepermissionEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include()
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;
    
    @Id
    @EqualsAndHashCode.Include()
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable=false, updatable=false)
    private PermissionEntity permission;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable=false, updatable=false)
    private RoleEntity role;


}




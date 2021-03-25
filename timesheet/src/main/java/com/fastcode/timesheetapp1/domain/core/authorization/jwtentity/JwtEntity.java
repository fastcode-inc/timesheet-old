package com.fastcode.timesheetapp1.domain.core.authorization.jwtentity;

import javax.persistence.*;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jwt_entity")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class JwtEntity extends AbstractEntity {

    @Basic
    @Column(name = "authentication_token", nullable = true)
    private String authenticationToken;

    @Basic
    @Column(name = "authorization_token", nullable = false)
    private String authorizationToken;

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "user_name", nullable = false,length =32)
    private String userName;


}




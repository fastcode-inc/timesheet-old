package com.fastcode.timesheetapp1.domain.core.timesheetstatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.timesheet.TimesheetEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.ShallowReference;

@Entity
@Table(name = "timesheetstatus")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TimesheetstatusEntity extends AbstractEntity {

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "statusname", nullable = false,length =255)
    private String statusname;

	@ShallowReference
    @OneToMany(mappedBy = "timesheetstatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TimesheetEntity> timesheetsSet = new HashSet<TimesheetEntity>();
    
    public void addTimesheets(TimesheetEntity timesheets) {        
    	timesheetsSet.add(timesheets);
        timesheets.setTimesheetstatus(this);
    }
    public void removeTimesheets(TimesheetEntity timesheets) {
        timesheetsSet.remove(timesheets);
        timesheets.setTimesheetstatus(null);
    }
    

}




package com.fastcode.timesheetapp1.domain.core.project;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
import com.fastcode.timesheetapp1.domain.core.customer.CustomerEntity;
import com.fastcode.timesheetapp1.domain.core.task.TaskEntity;
import com.fastcode.timesheetapp1.domain.core.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.ShallowReference;

@Entity
@Table(name = "project")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class ProjectEntity extends AbstractEntity {

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Basic
    @Column(name = "enddate", nullable = false)
    private LocalDate enddate;

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Basic
    @Column(name = "name", nullable = false,length =255)
    private String name;

    @Basic
    @Column(name = "startdate", nullable = false)
    private LocalDate startdate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customerid")
    private CustomerEntity customer;

	@ShallowReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasksSet = new HashSet<TaskEntity>();
    
    public void addTasks(TaskEntity tasks) {        
    	tasksSet.add(tasks);
        tasks.setProject(this);
    }
    public void removeTasks(TaskEntity tasks) {
        tasksSet.remove(tasks);
        tasks.setProject(null);
    }
    

}




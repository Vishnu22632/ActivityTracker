package com.synergytech.tms.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(nullable = false)
    private String taskName;
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;
    
    
    

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project; // Many tasks can belong to one project

    
    @ManyToOne
    private User assignedUser; // mapping with user
    
    
    
    // Enum for task status
    public enum TaskStatus {
//        NOT_STARTED,
        PENDING,
        IN_PROGRESS,
        COMPLETED,
//        ON_HOLD,
//        CANCELLED
    }

    public Task() {
    }

    public Task(String taskName, String description, TaskStatus taskStatus, Project project) {
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
        this.project = project;
    }

    // getter and setter

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
    
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.taskName);
        hash = 31 * hash + Objects.hashCode(this.description);
        hash = 31 * hash + Objects.hashCode(this.taskStatus);
        hash = 31 * hash + Objects.hashCode(this.project);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (!Objects.equals(this.taskName, other.taskName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (this.taskStatus != other.taskStatus) {
            return false;
        }
        return Objects.equals(this.project, other.project);
    }

}

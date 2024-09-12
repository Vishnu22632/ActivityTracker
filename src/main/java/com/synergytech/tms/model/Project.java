package com.synergytech.tms.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private String status = "Pending";

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;


    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks; // One-to-many relationship
    
    // for holding tasks count on each project
    @Transient
    private Long taskCount;

    @Transient
    private Long completedTaskCount;
    
    
    public Long getTaskCount() {
        return taskCount;
    }

    
    public void setTaskCount(Long taskCount) {
        this.taskCount = taskCount;
    }

    public Long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(Long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }
    
    
    // progress percentage
    public Long getProgressPercentage() {
        if (taskCount > 0) {
            return (completedTaskCount * 100) / taskCount;
        }
        
        return 0L;
    }
    
    // Method to find the project status based on the progress percentage
    public String getProjectStatus() {
    Long progressPercentage = getProgressPercentage();
    if (progressPercentage == 0) {
        return "Pending";
    } else if (progressPercentage == 100) {
        return "Completed";
    }
    return "Ongoing"; // Optional: Handle other cases if needed
}
    

    //constructor
    public Project() {
        
    }

    public Project(String name, String status, Date startDate, Date endDate, User manager, String description, List<Task> tasks, Long taskCount, Long completedTaskCount) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
        this.description = description;
        this.tasks = tasks;
        this.taskCount = taskCount;
        this.completedTaskCount = completedTaskCount;
    }
    
    
    
    public User getManager() {    
        return manager;
    }

    // getter and setter
    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

   @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Project other = (Project) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return "Project{" + "name=" + name + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", manager=" + manager + ", description=" + description + ", tasks=" + tasks + ", taskCount=" + taskCount + ", completedTaskCount=" + completedTaskCount + '}';
    }

    

    

}


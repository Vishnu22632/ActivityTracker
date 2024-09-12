package com.synergytech.tms.bean;

import com.synergytech.tms.model.Project;
import com.synergytech.tms.model.Task;
import com.synergytech.tms.model.User;
import com.synergytech.tms.repository.ProjectRepository;
import com.synergytech.tms.repository.TaskRepository;
import com.synergytech.tms.repository.UserRepository;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ProjectBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Project project = new Project();
    private List<Project> projects;

    private List<Task> tasks; // Add a list of tasks
    
    private List<User> users;
    
    

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private TaskRepository taskRepository; // inject TaskRepository
    
    
    @Inject
    private UserRepository userRepository;
    
    
    // constructor to initialize the project status as pending
    
    

    // Method to prepare a new project
    public void prepareNewProject() {
        this.project = new Project();
    }

    private boolean editing = false;

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
    
    @PostConstruct
    public void init() {
        projects = projectRepository.findAll();
        users = userRepository.findAll();
        
        projects.forEach(project -> {
            Long taskCount = taskRepository.countTasksByProject(project.getId());
            Long completedTaskCount = taskRepository.countCompletedTasksByProject(project.getId());
            project.setTaskCount(taskCount);
            project.setCompletedTaskCount(completedTaskCount);
        });
    }

    public String saveOrUpdateProject() {

        if (project.getId() == null) {
            projectRepository.create(project);
            addMessage("Success", "Project created successfully !!!");
        } else {
            projectRepository.update(project);
            addMessage("Success", "Project Updated successfully !!!");
        }
//        System.err.println("Invalid user : ");
       projects= projectRepository.findAll();
        project = new Project();
        setEditing(false);
        return null;

    }

    public List<Task> getTasksByProject(Project project) {
        return taskRepository.findByProject(project.getId());
    }

    public void prepareEditProject(Project selectedProject) {
        this.project = selectedProject;

        setEditing(true);
    }

    public List<Project> getProjects() {
        if (projects == null) {
            projects = projectRepository.findAll();
        }
        return projects;
    }

    public void deleteProject(Long id) {
        projectRepository.delete(id);
        addMessage("Success", "Project deleted successfully !!!");
        projects = projectRepository.findAll();
//        return "projectList?faces-redirect=true";
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long totalProjects() {
        return projectRepository.countTotalProjects();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Add a method to add messages
    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}

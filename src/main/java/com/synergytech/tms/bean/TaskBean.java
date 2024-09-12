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
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class TaskBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Task task;

    private List<Task> tasks;

    private List<User> users; // to hold users

    // list of projects
    private List<Project> projects;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private ProjectRepository projectRepository; // Inject projectRepository

    @PostConstruct
    public void init() {
        users = userRepository.findAll();
        task = new Task();
        tasks = taskRepository.findAll();
        projects = projectRepository.findAll();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    //getter for enum values
    public List<Task.TaskStatus> getTaskStatusValues() {
        return List.of(Task.TaskStatus.values());
    }

    // Unified save or update task method
    public void saveOrUpdateTask() {
        try {
            if (task.getProject() == null || task.getProject().getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Project is required"));
//                return null;
            }

            if (task.getId() == null) {
                taskRepository.create(task);
                addMessage("Success", "Task created successfully !!!");
            } else {
                taskRepository.update(task);
                addMessage("Success", "Task Updated successfully !!!");
            }

            tasks = taskRepository.findAll(); // Refresh the task list

//            return "taskList.xhtml?faces-redirect=true"; // Redirect to taskList.xhtml
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            e.printStackTrace();
//            return null;
        }
    }

    public void deleteTask(Long id) {
        taskRepository.delete(id);
        addMessage("Success", "Task deleted successfully !!!");
        tasks = taskRepository.findAll(); // Refresh the task list
       
        // You can also update the list programmatically if needed
        // Update the UI components programmatically if needed
//        PrimeFaces.current().ajax().update("projectListForm:projectTable projectListForm:grl");

          

//        return "listTask?faces-redirect=true";
    }

//   
    // Add a method to add messages
    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void prepareEditTask(Task selectedTask) {
        this.task = selectedTask;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // changing the status 
    public void markTaskInProgress(Task task) {
        task.setTaskStatus(Task.TaskStatus.IN_PROGRESS);
        taskRepository.update(task);
        addMessage("Success", "Task marked as In Progress!");
        tasks = taskRepository.findAll(); // Refresh the task list
    }

    public void markTaskCompleted(Task task) {
        task.setTaskStatus(Task.TaskStatus.COMPLETED);
        taskRepository.update(task);
        addMessage("Success", "Task marked as Completed!");
        tasks = taskRepository.findAll(); // Refresh the task list
    }

}

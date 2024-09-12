
package com.synergytech.tms.bean;

import com.synergytech.tms.model.User;
import com.synergytech.tms.repository.UserRepository;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class LoginBean {

    @Inject
    private UserRepository userRepository;
    
    @Inject
    private SessionBean sessionBean;

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        // Authenticate the user using the UserRepository's authenticate method
        User user = userRepository.findUserByEmailAndPassword(email, password);
        if (user != null) {
            //Successful login, store user details in the session
            sessionBean.storeUserInSession(user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Login successful"));
            return "user_list?faces-redirect=true";
        } else {
            // Authentication failed, show an error message
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", "Please try again."));
            return null; //stay on login page
        }
    }
    
    
//    public String login() {
//        User user = userRepository.findUserByEmailAndPassword(email, password);
//        
//        // user SessionBean, set the username in sesssion bean
//        // Authentication successful, set the username in session bean
//        UserSessionBean sessionBean = FacesContext.getCurrentInstance()
//                                 .getApplication()
//                                 .
////                                 .evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{userSessionBean}", UserSessionBean.class);
//        
//        sessionBean.setUsername(user.getFullName());
//        
//        
//        
//        
//        if (user != null) {
//            // Redirect to the welcome page or dashboard
//            return "home.xhtml?faces-redirect=true";
//        } else {
//            // Show error message on the same page
//            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
//                new javax.faces.application.FacesMessage("Invalid email or password."));
//            return null; // stay on the same page
//        }
//    }
    
    
    
}
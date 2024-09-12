
package com.synergytech.tms.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UserSessionBean {

    private String username; // Store the logged-in user's username

    // Method to set username upon successful login
    public void setUsername(String username) {
        this.username = username;
    }

    // Method to get the current username
    public String getUsername() {
        return username;
    }

    
}
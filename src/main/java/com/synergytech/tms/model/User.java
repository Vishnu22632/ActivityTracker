package com.synergytech.tms.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;
    
    
    @Column(nullable = false)
    private String address;

//    @OneToMany(mappedBy = "manager")
//    private List<Project> managedProjects;
//    
    
    
    // constructors

    public User() {
    }

    public User(String fullName, String email, String password, UserRole userRole, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.address = address;
    }

    
   
    
  // Getters and Setters
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

   

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

     @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId());
}

@Override
public int hashCode() {
    return Objects.hash(getId());
}

    @Override
    public String toString() {
        return "User{" + "fullName=" + fullName + ", email=" + email + ", password=" + password + ", userRole=" + userRole + ", address=" + address + '}';
    }

   





}

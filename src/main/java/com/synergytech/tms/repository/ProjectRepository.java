package com.synergytech.tms.repository;

import com.synergytech.tms.model.Project;
import javax.ejb.Stateless;

@Stateless
public class ProjectRepository extends BaseRepository<Project, Long> {

    public ProjectRepository() {
        super(Project.class);
    }

    public long countTotalProjects() {
        return count(); // Already adjusted in BaseRepository
    }

    public void delete(Project project) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

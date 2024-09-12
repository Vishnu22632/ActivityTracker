package com.synergytech.tms.converter;

import com.synergytech.tms.model.Project;
import com.synergytech.tms.repository.ProjectRepository;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "projectConverter")
public class ProjectConverter implements Converter {

    @Inject
    private ProjectRepository projectRepository;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return projectRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Invalid project ID"));
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || "".equals(o)) {
            return "";
        }
        if (o instanceof Project) {
            return String.valueOf(((Project) o).getId());
        } else {
            throw new IllegalArgumentException("Object is not a valid Project");
        }
    }
}

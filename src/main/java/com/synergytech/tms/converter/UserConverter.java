package com.synergytech.tms.converter;

import com.synergytech.tms.model.User;
import com.synergytech.tms.repository.UserRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter<User> {

    @Inject
    private UserRepository userRepository;

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return userRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Conversion error: " + e.getMessage(), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, User user) {
        if (user == null) {
            return "";
        }
        return user.getId() != null ? user.getId().toString() : "";
    }
}

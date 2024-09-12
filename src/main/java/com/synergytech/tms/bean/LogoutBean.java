
package com.synergytech.tms.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class LogoutBean {

    public void logout() throws IOException {
        // Invalidate the session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // Redirect to loginForm.xhtml
        FacesContext.getCurrentInstance().getExternalContext().redirect("loginForm.xhtml");
    }
}
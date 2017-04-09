/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.auth;

import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
public class AuthorizationListener implements PhaseListener {

    private static final long serialVersionUID = 1535710966062595554L;
    private static final Logger LOG = Logger.getLogger(AuthorizationListener.class.getName());

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("logIn.xhtml") > -1);

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
        } else {
            Object username = session.getAttribute("username");
            Object admin = session.getAttribute("admin");

            if (!(isLoginPage) && (username == null || username.toString().equals(""))) {

                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");

            } else if (!(isLoginPage) && (username == null || username.toString().equals(""))) {

                Boolean Admin = (Boolean) admin;
                HttpServletRequest origRequest
                        = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                String uri = origRequest.getRequestURI();
                String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                String urlPath = uri.replace(url, "");
                if(Admin){
                    
                }else{
                    
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}

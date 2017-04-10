/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.auth;

import com.betel.flowers.service.OpcionSistemaService;
import com.betel.flowers.service.TipoUsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author luis
 */
public class AuthorizationListener implements PhaseListener {

    private static final long serialVersionUID = 1535710966062595554L;
    private static final Logger LOG = Logger.getLogger(AuthorizationListener.class.getName());
    @Inject
    private OpcionSistemaService opcionSistemaService;
    @Inject
    private TipoUsuarioService tipoUsuarioService;

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);
        boolean isRegisterPage = (currentPage.lastIndexOf("register.xhtml") > -1);

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
        } else {
            Object username = session.getAttribute("username");
            Object admin = session.getAttribute("admin");
            Object tipoUsuarioCodigo = session.getAttribute("tipoUsuarioCodigo");

            if (!(isLoginPage || isRegisterPage) && (username == null || username.toString().equals(""))) {

                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");

            } else if (!isLoginPage && admin != null && tipoUsuarioCodigo != null) {

                Boolean Admin = (Boolean) admin;
                HttpServletRequest origRequest
                        = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                String uri = origRequest.getRequestURI();
                String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                String urlPath = uri.replace(url, "");
                if (Admin) {
                    List<String> urls = new ArrayList<>();
                    urls = this.opcionSistemaService.obtenerUrlsAdmin();
                    if (!views(urls, urlPath)) {
                        NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                        nh.handleNavigation(facesContext, null, "menu");
                    }
                    LOG.log(Level.INFO, "El usuario a iniciado sesion", username);
                } else {
                    List<String> urls = new ArrayList<>();
                    Integer code = new Integer(tipoUsuarioCodigo.toString());
                    urls = this.tipoUsuarioService.obtenerUrlsTipoUsuario(code);
                    if (!views(urls, urlPath)) {
                        NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                        nh.handleNavigation(facesContext, null, "menu");
                    }
                    LOG.log(Level.INFO, "El usuario a iniciado sesion", username);
                }
            }
        }
    }

    public Boolean views(List<String> urls, String view) {
        Boolean exito = false;
        for (String url : urls) {
            if (url.equals(view)) {
                exito = true;
                break;
            }
        }
        return exito;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}

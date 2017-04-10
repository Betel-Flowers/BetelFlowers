/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.UsuarioService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author luis
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2272147739224429341L;

    private Usuario usuario;

    @Inject
    private CredencialBean session;
    @Inject
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
    }

    public void login(ActionEvent evt) {
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Boolean LoggedIn = Boolean.FALSE;
        if (this.usuarioService.existsUsername(this.usuario)) {
            if (this.usuarioService.stateUsername(this.usuario)) {
                if (this.usuarioService.checkPassword(this.usuario)) {
                    this.session.startSession(this.usuario);
                    url = url + "/faces/views/betel.xhtml";
                    LoggedIn = Boolean.TRUE;
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "Username y/o contrseña incorreoctos.");
                }
            } else {
                FacesUtil.addMessageInfo("El no esta habilitado porfavor comuniquese con el administrador.");
            }
        } else {
            FacesUtil.addMessageError(null, "El usuario no se encuentra registrado.");
        }
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

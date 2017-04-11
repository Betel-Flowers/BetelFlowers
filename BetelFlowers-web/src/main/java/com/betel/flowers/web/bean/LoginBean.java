/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.TipoUsuario;
import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.TipoUsuarioService;
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
    @Inject
    private TipoUsuarioService tipoUsuarioService;

    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
        this.createAdmin();
    }

    public void login(ActionEvent evt) {
        RequestContext context = RequestContext.getCurrentInstance();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Boolean LoggedIn = Boolean.FALSE;
        if (this.usuarioService.existsUsername(this.usuario)) {
            if (this.usuarioService.stateUsername(this.usuario)) {
                if (this.usuarioService.checkPassword(this.usuario)) {
                    Usuario loginUser = this.usuarioService.findByUsername(this.usuario);
                    this.session.startSession(loginUser);
                    url = url + "/faces/views/betel.xhtml";
                    LoggedIn = Boolean.TRUE;
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "Username y/o contrseña incorreoctos.");
                }
            } else {
                FacesUtil.addMessageInfo("El usuario no esta habilitado porfavor comuniquese con el administrador.");
            }
        } else {
            FacesUtil.addMessageError(null, "El usuario no se encuentra registrado.");
        }
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", url);
    }

    private void createAdmin() {
        Usuario admin = new Usuario();
        admin.setEstado(Boolean.TRUE);
        admin.setUsername("admin.betel.2017");
        admin.setPassword("admin.betel.2017");
        admin = this.usuarioService.findByUsername(admin);
        if (admin.getCodigo() == null && admin.getTipoUsuario() == null) {
            TipoUsuario tipo = new TipoUsuario();
            tipo.setNombre("Admin");
            tipo.setAdmin(Boolean.TRUE);
            TipoUsuario mTipo = this.tipoUsuarioService.findByNombre(tipo);
            if (mTipo.getId() == null) {
                this.tipoUsuarioService.insert(tipo);
            }
            Usuario adminBetel = new Usuario();
            adminBetel.setEstado(Boolean.TRUE);
            adminBetel.setUsername("admin.betel.2017");
            adminBetel.setPassword("admin.betel.2017");
            adminBetel.getInfoPersonal().setApellidos("admin");
            adminBetel.getInfoPersonal().setNombres("admin");
            adminBetel.getInfoPersonal().setCedula("0000000000");
            adminBetel.getInfoPersonal().setMovil("0000000000");
            Usuario mUsername = this.usuarioService.findByUsername(adminBetel);
            if (mUsername.getId() == null) {
                TipoUsuario mTipoAdmin = this.tipoUsuarioService.findByNombre(tipo);
                adminBetel.setTipoUsuario(mTipoAdmin);
                this.usuarioService.insert(adminBetel);
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

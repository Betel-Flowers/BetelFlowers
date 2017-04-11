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
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable {
    
    private Usuario nuevo;
    private String cpassword;
    
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private TipoUsuarioService tipousuarioService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new Usuario();
        this.cpassword = "";
        this.createTipoUsuarioBlank();
    }
    
    public void add(ActionEvent evt) {
        if (this.nuevo.getPassword().equals(this.cpassword)) {
            Usuario username = this.usuarioService.findByUsername(this.nuevo);
            if (username.getId() == null) {
                Usuario useremail = this.usuarioService.findByEmail(this.nuevo.getInfoPersonal().getEmail());
                if (useremail.getId() == null) {
                    TipoUsuario tipo = new TipoUsuario();
                    tipo.setNombre("SIN-TIPO-USUARIO");
                    TipoUsuario mtipo = this.tipousuarioService.findByNombre(tipo);
                    this.nuevo.setTipoUsuario(mtipo);
                    Boolean exito = this.usuarioService.insert(this.nuevo);
                    if (exito) {
                        FacesUtil.addMessageInfo("El usuario a sido registrado con exito.");
                        this.init();
                    } else {
                        FacesUtil.addMessageError(null, "El usuario no sea registrado.");
                        this.init();
                    }
                } else {
                    FacesUtil.addMessageWarn(null, "Ya existe un usuario registrado con ese e-mail.");
                }
            } else {
                FacesUtil.addMessageWarn(null, "Ya existe un usuario registrado con ese username.");
            }
        } else {
            FacesUtil.addMessageWarn(null, "El password no coincide.");
        }
    }
    
    private void createTipoUsuarioBlank() {
        TipoUsuario tipo = new TipoUsuario();
        tipo.setNombre("SIN-TIPO-USUARIO");
        TipoUsuario mTipo = this.tipousuarioService.findByNombre(tipo);
        if (mTipo.getCodigo() == null) {
            this.tipousuarioService.insert(tipo);
        }
        
    }
    
    public Usuario getNuevo() {
        return nuevo;
    }
    
    public void setNuevo(Usuario nuevo) {
        this.nuevo = nuevo;
    }
    
    public String getCpassword() {
        return cpassword;
    }
    
    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }
}

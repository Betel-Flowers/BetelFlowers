/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.UsuarioService;
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
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2272147739224429341L;

    private Usuario nuevo;

    @Inject
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new Usuario();
    }

    public void login(ActionEvent evt){
        
    }
    
    public Usuario getNuevo() {
        return nuevo;
    }

    public void setNuevo(Usuario nuevo) {
        this.nuevo = nuevo;
    }

}

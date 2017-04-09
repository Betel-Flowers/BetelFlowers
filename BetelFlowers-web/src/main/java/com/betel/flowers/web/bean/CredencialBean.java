/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.OpcionSistemaService;
import com.betel.flowers.web.bean.util.menu.ItemMenu;
import com.betel.flowers.web.bean.util.menu.SubMenu;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author luis
 */
@Named(value = "credencialBean")
@SessionScoped
public class CredencialBean implements Serializable {

    private static final long serialVersionUID = 5955970909768673736L;

    private Usuario userSession = new Usuario();
    private MenuModel piMenu = new DefaultMenuModel();
    private List<SubMenu> menu = new ArrayList<>();
    private String layoutmenu;

    @Inject
    private OpcionSistemaService opcionSistemaService;

    private void init() {
        this.userSession = new Usuario();
        this.piMenu = new DefaultMenuModel();
        this.menu = new ArrayList<>();
        this.layoutmenu = "layoutmenu.xhtml";
    }

    public void startSession(Usuario usuario) {
        this.userSession = usuario;
        this.loadLayoutMenu(this.userSession);
        this.loadDataSession(this.userSession);
    }

    private void loadLayoutMenu(Usuario usuario) {
        if (usuario.getTipoUsuario().getAdmin()) {
            this.layoutmenu = "layoutmenu.xhtml";
        } else {
            this.layoutmenu = "layoutusermenu.xhtml";
            this.loadMenu(this.userSession);
            this.createMenu();
        }
    }

    private void loadDataSession(Usuario usuario) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("codigo", usuario.getCodigo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("username", usuario.getUsername());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("admin", usuario.getTipoUsuario().getAdmin());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("tipoUsuario", usuario.getTipoUsuario().getNombre());
    }

    private void loadMenu(Usuario usuario) {
        List<OpcionSistema> unique = this.selectSubmenuLabel(usuario.getTipoUsuario().getOpcionesSistema());
        for (int i = 0; i < unique.size(); i++) {
            SubMenu subMenu = new SubMenu();
            subMenu.setLabel(unique.get(i).getSubmenu_label());
            for (int j = 0; j < usuario.getTipoUsuario().getOpcionesSistema().size(); j++) {
                if (unique.get(i).getSubmenu_label().equals(usuario.getTipoUsuario().getOpcionesSistema().get(j).getSubmenu_label())) {
                    ItemMenu item = new ItemMenu();
                    item.setValue(usuario.getTipoUsuario().getOpcionesSistema().get(j).getMenuitem_value());
                    item.setOutcome(usuario.getTipoUsuario().getOpcionesSistema().get(j).getMenuitem_outcome());
                    subMenu.getItems().add(item);
                }
            }
            this.getMenu().add(subMenu);
        }
    }

    private void createMenu() {
        if (this.getMenu() != null) {
            for (SubMenu subMenu : this.getMenu()) {
                DefaultSubMenu defSubmenu = new DefaultSubMenu(subMenu.getLabel());
                for (ItemMenu itemMenu : subMenu.getItems()) {
                    DefaultMenuItem defMenuItem = new DefaultMenuItem(itemMenu.getValue());
                    defMenuItem.setOutcome(itemMenu.getOutcome());
                    defSubmenu.addElement(defMenuItem);
                }
                this.piMenu.addElement(defSubmenu);
            }
        }
    }

    private List<OpcionSistema> selectSubmenuLabel(List<OpcionSistema> menu) {

        List<OpcionSistema> unique = new ArrayList<>();
        if (menu != null && !menu.isEmpty()) {
            unique.add(menu.get(0));
            for (int i = 0; i < menu.size(); i++) {
                if (!(menu.get(i).getSubmenu_label().equals(unique.get(unique.size() - 1).getSubmenu_label()))) {
                    unique.add(menu.get(i));
                }
            }
        }
        return unique;
    }

    public void logout(ActionEvent event) {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/index.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute(this.userSession.getUsername());
        session.invalidate();
        this.init();
        context.addCallbackParam("loggerOut", true);
        context.addCallbackParam("ruta", url);
    }

    public Usuario getUserSession() {
        return userSession;
    }

    public void setUserSession(Usuario userSession) {
        this.userSession = userSession;
    }

    public MenuModel getPiMenu() {
        return piMenu;
    }

    public void setPiMenu(MenuModel piMenu) {
        this.piMenu = piMenu;
    }

    public List<SubMenu> getMenu() {
        return menu;
    }

    public void setMenu(List<SubMenu> menu) {
        this.menu = menu;
    }

    public String getLayoutmenu() {
        return layoutmenu;
    }

    public void setLayoutmenu(String layoutmenu) {
        this.layoutmenu = layoutmenu;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.UsuarioService;
import com.betel.flowers.web.bean.util.DeleteFileRun;
import com.betel.flowers.web.bean.util.UploadFileRun;
import com.betel.flowers.web.bean.util.menu.ItemMenu;
import com.betel.flowers.web.bean.util.menu.SubMenu;
import com.betel.flowers.web.util.FacesUtil;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
    private static final Logger LOG = Logger.getLogger(CredencialBean.class.getName());
    private static final Integer sizeImage = 5500000;

    private Usuario userSession = new Usuario();
    private MenuModel piMenu = new DefaultMenuModel();
    private List<SubMenu> menu = new ArrayList<>();
    private String layoutmenu;
    private String cpassword;
    private UploadedFile file;

    @Inject
    private UsuarioService usuarioService;

    private void init() {
        this.userSession = new Usuario();
        this.piMenu = new DefaultMenuModel();
        this.menu = new ArrayList<>();
        this.layoutmenu = "layoutmenu.xhtml";
    }

    public void startSession(Usuario usuario) {
        this.userSession = usuario;
        this.loadDataSession(this.userSession);
        this.loadLayoutMenu(this.userSession);
        this.userSession.getInfoPersonal().setCodigoFoto(this.codigoFoto());
    }

    private void loadLayoutMenu(Usuario usuario) {
        if (usuario.getTipoUsuario().getAdmin()) {
            this.layoutmenu = "layoutmenu.xhtml";
        } else {
            this.layoutmenu = "layoutusermenu.xhtml";
            this.loadMenu(usuario);
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
                put("tipoUsuarioCodigo", usuario.getTipoUsuario().getCodigo());
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

    private String codigoFoto() {
        GregorianCalendar calendario = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
        return "BETEL-U" + RandomStringUtils.randomNumeric(4) + "-IMG-" + format.format(calendario.getTime());
    }

    public void modifyInfo(ActionEvent evt) {
        Boolean exito = this.usuarioService.update(this.userSession);
        if (exito) {
            FacesUtil.addMessageInfo("Se actualizo exitosamente.");
        } else {
            FacesUtil.addMessageError(null, "No actualizo.");
        }
    }

    public void changePassword(ActionEvent evt) {
        if (this.userSession.getPassword().equals(this.cpassword)) {
            Boolean exito = this.usuarioService.updatePassword(this.userSession);
            if (exito) {
                FacesUtil.addMessageInfo("Se modifico el password con exito.");
            } else {
                FacesUtil.addMessageError(null, "No se modifico el password con exito.");
            }
        } else {
            FacesUtil.addMessageInfo("El password no coincide.");
        }
    }

    public void handleSaveFoto(FileUploadEvent event) {
        this.setFile(event.getFile());
        if (this.getFile() != null) {
            if ((getFile().getFileName().endsWith(".png")
                    || getFile().getFileName().endsWith(".PNG")
                    || getFile().getFileName().endsWith(".jpg")
                    || getFile().getFileName().endsWith(".JPG")
                    || getFile().getFileName().endsWith(".jpg")
                    || getFile().getFileName().endsWith(".JPEG")
                    || getFile().getFileName().endsWith(".jpeg"))
                    && this.getFile().getSize() < sizeImage) {
                try {
                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    String ipAdress = request.getLocalAddr();
                    String filepath = "http://" + ipAdress + "/usuarios/";
                    String url = "/var/www/html/usuarios/";
                    String ext = getFile().getContentType();
                    DeleteFileRun deleteFile = new DeleteFileRun(url, this.userSession.getInfoPersonal().getCodigoFoto(), ext.replace("image/", ""));
                    deleteFile.run();
                    this.userSession.getInfoPersonal().setCodigoFoto(this.codigoFoto());
                    UploadFileRun upload = new UploadFileRun(url, this.userSession.getInfoPersonal().getCodigoFoto(), ext.replace("image/", ""), getFile().getInputstream());
                    upload.run();
                    if (upload.getExito()) {
                        this.userSession.getInfoPersonal().setUrlFoto(filepath + this.userSession.getInfoPersonal().getCodigoFoto() + "." + ext.replace("image/", ""));
                        LOG.log(Level.INFO, "dir IP save: " + filepath);
                        this.userSession.getInfoPersonal().setLoadFoto(Boolean.TRUE);
                        this.usuarioService.update(this.userSession);
                        FacesUtil.addMessageInfo("Se ha agregado la imagen.");
                    }
                } catch (IOException ex) {
                    //log.level.error("Error al subir la imagen", ex);
                }
            } else {
                FacesUtil.addMessageInfo("Tamaño maximo de imagen 5.5 MB.");
            }

        }
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

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}

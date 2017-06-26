/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.ViewHandler;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author luis
 */
@Named(value = "navigationUrlBean")
@ViewScoped
public class NavigationUrlBean implements Serializable {

    private String uri;
    private String url;
    private String ipAddress;
    private String portNumber;
    private String dirVenta;

    @PostConstruct
    public void init() {
        HttpServletRequest origRequest
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String uri = origRequest.getRequestURI();
        String ipAddress = origRequest.getLocalAddr();
        String portNumber = "" + origRequest.getLocalPort();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.setIpAddress(ipAddress);
        this.setPortNumber(portNumber);
        this.setUrl(url);
        this.setUri(uri);
        final String viewId = "/views/venta.xhtml";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
        String actionUrl = viewHandler.getActionURL(facesContext, viewId);
        this.setDirVenta(actionUrl);
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getDirVenta() {
        return dirVenta;
    }

    public void setDirVenta(String dirVenta) {
        this.dirVenta = dirVenta;
    }

}

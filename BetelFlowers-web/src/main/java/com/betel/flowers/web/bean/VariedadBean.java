/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Bloque;
import com.betel.flowers.model.Especie;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BloqueService;
import com.betel.flowers.service.EspecieService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.DeleteFileRun;
import com.betel.flowers.web.bean.util.UploadFileRun;
import com.betel.flowers.web.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author luis
 */
@Named(value = "variedadBean")
@ViewScoped
public class VariedadBean implements Serializable {

    private static final long serialVersionUID = 720235995057834086L;
    private static final Logger LOG = Logger.getLogger(VariedadBean.class.getName());

    private Variedad nuevo;
    private Variedad selected;
    private List<Variedad> variedades;
    private List<Integer> ramosExists;

    private static final Integer sizeImage = 5500000;
    private UploadedFile file;

    private String urlSelected;

    @Inject
    private VariedadService variedadService;
    @Inject
    private EspecieService especieService;
    @Inject
    private BloqueService bloqueServie;

    @PostConstruct
    public void init() {
        this.nuevo = new Variedad();
        this.nuevo.setUsername("usertest");//usertest
        this.nuevo.setCodigoFoto(this.codigoFoto());
        this.selected = null;
        this.file = null;
        this.variedades = this.variedadService.obtenerListFlag(1);
        this.ramosExists = this.variedadService.obtenerRamos();
        if (this.variedades == null) {
            this.variedades = new ArrayList<>();
        }
        if (this.ramosExists == null) {
            this.ramosExists = new ArrayList<>();
        }
    }

    private String codigoFoto() {
        GregorianCalendar calendario = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
        return "BETEL-V" + RandomStringUtils.randomNumeric(4) + "-IMG-" + format.format(calendario.getTime());
    }

    public void add(ActionEvent evt) {
        Especie especie = this.especieService.findByCodigo(this.nuevo.getEspecie());
        this.nuevo.setEspecie(especie);
        if (this.nuevo.getBloques() != null && !this.nuevo.getBloques().isEmpty()) {
            Boolean exito = this.variedadService.insert(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Por favor seleccione por lo menos un bloque.");
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Especie especie = this.especieService.findByCodigo(this.selected.getEspecie());
            this.selected.setEspecie(especie);
            this.selected.setBloques(this.nuevo.getBloques());
            Boolean exito = this.variedadService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = variedadService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void savefoto(ActionEvent evt) {
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
                    String filepath = "http://" + ipAdress + "/variedades/";
                    String url = "/var/www/html/variedades/";
                    String ext = getFile().getContentType();
                    UploadFileRun upload = new UploadFileRun(url, this.nuevo.getCodigoFoto(), ext.replace("image/", ""), getFile().getInputstream());
                    upload.run();
                    if (upload.getExito()) {
                        this.nuevo.setUrlFoto(filepath + this.nuevo.getCodigoFoto() + "." + ext.replace("image/", ""));
                        LOG.log(Level.INFO, "dir IP save: " + filepath);
                    }
                } catch (IOException ex) {
                    //log.level.error("Error al subir la imagen", ex);
                }
            }

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
                    String filepath = "http://" + ipAdress + "/variedades/";
                    String url = "/var/www/html/variedades/";
                    String ext = getFile().getContentType();
                    UploadFileRun upload = new UploadFileRun(url, this.nuevo.getCodigoFoto(), ext.replace("image/", ""), getFile().getInputstream());
                    upload.run();
                    if (upload.getExito()) {
                        this.nuevo.setUrlFoto(filepath + this.nuevo.getCodigoFoto() + "." + ext.replace("image/", ""));
                        LOG.log(Level.INFO, "dir IP save: " + filepath);
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

    public void updatefoto(ActionEvent evt) {
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
                    String filepath = "http://" + ipAdress + "/variedades/";
                    String url = "/var/www/html/variedades/";
                    String ext = getFile().getContentType();
                    UploadFileRun upload = new UploadFileRun(url, this.selected.getCodigoFoto(), ext.replace("image/", ""), getFile().getInputstream());
                    upload.run();
                    if (upload.getExito()) {
                        this.selected.setUrlFoto(filepath + this.selected.getCodigoFoto() + "." + ext.replace("image/", ""));
                        LOG.log(Level.INFO, "dir IP save: " + filepath);
                    }
                } catch (IOException ex) {
                    //log.level.error("Error al subir la imagen", ex);
                }
            }

        }
    }

    public void handleUpdateFoto(FileUploadEvent event) {
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
                    String filepath = "http://" + ipAdress + "/variedades/";
                    String url = "/var/www/html/variedades/";
                    String ext = getFile().getContentType();
                    DeleteFileRun deleteFile = new DeleteFileRun(url, this.selected.getCodigoFoto(), ext.replace("image/", ""));
                    deleteFile.run();
                    this.selected.setCodigoFoto(this.codigoFoto());
                    UploadFileRun upload = new UploadFileRun(url, this.selected.getCodigoFoto(), ext.replace("image/", ""), getFile().getInputstream());
                    upload.run();
                    if (upload.getExito()) {
                        this.selected.setUrlFoto(filepath + this.selected.getCodigoFoto() + "." + ext.replace("image/", ""));
                        LOG.log(Level.INFO, "dir IP save: " + filepath);
                        FacesUtil.addMessageInfo("Se ha actualizado la imagen.");
                    }
                } catch (IOException ex) {
                    //log.level.error("Error al subir la imagen", ex);
                }
            } else {
                FacesUtil.addMessageInfo("Tamaño maximo de imagen 5.5 MB.");
            }

        }
    }

    public List<Bloque> listBardodeInsideList(Variedad barcodeItem) {
        List<Bloque> list = new ArrayList<>();
        if (barcodeItem != null) {
            if (barcodeItem.getBloques() != null && !barcodeItem.getBloques().isEmpty()) {
                list = barcodeItem.getBloques();
            }
        }
        return list;
    }

    public Variedad getNuevo() {
        return nuevo;
    }

    public void setNuevo(Variedad nuevo) {
        this.nuevo = nuevo;
    }

    public Variedad getSelected() {
        return selected;
    }

    public void setSelected(Variedad selected) {
        this.selected = selected;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getUrlSelected() {
        return urlSelected;
    }

    public void setUrlSelected(String urlSelected) {
        this.urlSelected = urlSelected;
    }

    public List<Integer> getRamosExists() {
        return ramosExists;
    }

    public void setRamosExists(List<Integer> ramosExists) {
        this.ramosExists = ramosExists;
    }

}

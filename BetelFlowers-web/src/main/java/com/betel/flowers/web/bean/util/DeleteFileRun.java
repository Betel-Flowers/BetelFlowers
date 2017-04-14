/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class DeleteFileRun extends Thread implements Serializable {

    private static final Logger log = Logger.getLogger(DeleteFileRun.class.getName());
    private static final long serialVersionUID = 4850689043674193735L;

    String url;
    String name;
    String ext;
    private Boolean exito;

    public DeleteFileRun(String url, String name, String ext) {
        this.url = url;
        this.name = name;
        this.ext = ext;
    }

    @Override
    public void run() {
        Boolean succesefull = Boolean.FALSE;
        File fileDelete = new File(url + name + "." + ext);
        if (fileDelete.delete()) {
            succesefull = Boolean.TRUE;
            log.log(Level.INFO, "Se elimino el archivo: " + url + name + "." + ext);
        } else {
            log.log(Level.INFO, "No se elimino el archivo: " + url + name + "." + ext);
        }
        if (succesefull) {
            try {
                this.finalize();
                this.exito = true;
            } catch (Throwable ex) {
                log.log(Level.SEVERE, "Error al procesar imagen", ex);
            }
        } else {
            this.interrupt();
            this.exito = false;
        }
    }

    public Boolean getExito() {
        return exito;
    }

    public void setExito(Boolean exito) {
        this.exito = exito;
    }

}

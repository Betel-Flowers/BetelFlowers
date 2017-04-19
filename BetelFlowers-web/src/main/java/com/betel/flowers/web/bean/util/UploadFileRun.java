/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luis
 */
public class UploadFileRun extends Thread implements Serializable {

    private static final Logger log = Logger.getLogger(UploadFileRun.class.getName());
    private static final long serialVersionUID = -7924712329208471259L;

    String url;
    String name;
    String ext;
    InputStream input;
    private Boolean exito;

    public UploadFileRun(String url, String name, String ext, InputStream input) {
        this.url = url;
        this.name = name;
        this.ext = ext;
        this.input = input;
    }

    @Override
    public void run() {
        Boolean succesefull = Boolean.FALSE;
        Path path = Paths.get(url);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.log(Level.INFO, "Directory is created!");
            } catch (IOException e) {
                //fail to create directory
                log.log(Level.SEVERE, "Failed to create directory! " + e.getMessage());
            }
        }

        File fileDelete = new File(url + name + "." + ext);
        if (fileDelete.delete()) {
            log.log(Level.INFO, "Se elimino el archivo: " + url + name + "." + ext);
        } else {
            log.log(Level.INFO, "No se elimino el archivo: " + url + name + "." + ext);
        }
        
        File file = new File(url + name + "." + ext);
        try {
            byte[] imgbytes = IOUtils.toByteArray(input);
            InputStream in = new ByteArrayInputStream(imgbytes);
            BufferedImage bImageFromConvert = ImageIO.read(in);
            ImageIO.write(bImageFromConvert, ext, file);
            succesefull = Boolean.TRUE;
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error al guardar la imagen en:" + file.getPath(), e);
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

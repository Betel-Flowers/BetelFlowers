/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "RegistroExportacion", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroExportacion extends BaseEntity {

    private Integer codigo;
    private Integer numeroRamos;
    private Integer numeroTallosRamo;
    private Integer longitud;
    private String glongitud;
    private String puntoCorte;
    private Integer totalTallos;
    private String barcode;
    private String xml;
    private String html;
    private String pdf;
    private String urlPdf;
    private String username;
    private Integer flag;

    @Reference
    private BodegaVirtual bodega;
    @Reference
    private Variedad variedad;
    @Reference
    private List<Rendimiento> redimientos;

    public RegistroExportacion() {
        this.numeroRamos = 1;
        this.numeroTallosRamo = 1;
        this.bodega = new BodegaVirtual();
        this.variedad = new Variedad();
        this.redimientos = new ArrayList<>();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroRamos() {
        return numeroRamos;
    }

    public void setNumeroRamos(Integer numeroRamos) {
        this.numeroRamos = numeroRamos;
    }

    public Integer getNumeroTallosRamo() {
        return numeroTallosRamo;
    }

    public void setNumeroTallosRamo(Integer numeroTallosRamo) {
        this.numeroTallosRamo = numeroTallosRamo;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public String getGlongitud() {
        return glongitud;
    }

    public void setGlongitud(String glongitud) {
        this.glongitud = glongitud;
    }

    public String getPuntoCorte() {
        return puntoCorte;
    }

    public void setPuntoCorte(String puntoCorte) {
        this.puntoCorte = puntoCorte;
    }

    public Integer getTotalTallos() {
        return getNumeroRamos() * getNumeroTallosRamo();
    }

    public void setTotalTallos(Integer totalTallos) {
        this.totalTallos = totalTallos;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getXml() {
        return xml;
    }

    public String getHtml() {
        return html;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public BodegaVirtual getBodega() {
        return bodega;
    }

    public void setBodega(BodegaVirtual bodega) {
        this.bodega = bodega;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public List<Rendimiento> getRedimientos() {
        return redimientos;
    }

    public void setRedimientos(List<Rendimiento> redimientos) {
        this.redimientos = redimientos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegistroExportacion other = (RegistroExportacion) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroExportacion{" + "codigo=" + codigo + ", numeroRamos=" + numeroRamos + ", numeroTallosRamo=" + numeroTallosRamo + ", longitud=" + longitud + ", puntoCorte=" + puntoCorte + ", totalTallos=" + totalTallos + ", barcode=" + barcode + ", username=" + username + ", bodega=" + bodega + ", variedad=" + variedad + '}';
    }

}

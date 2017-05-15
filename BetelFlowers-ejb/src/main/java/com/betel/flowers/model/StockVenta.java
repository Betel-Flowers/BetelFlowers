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
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "StockVentas", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class StockVenta extends BaseEntity {

    private Integer codigo;
    private Integer cantidadCajas;
    private Integer totalTallos;
    private String barcode;
    private String message;
    private String xml;
    private String html;
    private String pdf;
    private String urlPdf;
    private String username;
    private Integer flag;

    @Embedded
    private List<ItemVariedadStock> detalleCajaStock;
    @Reference
    private TipoCaja caja;

    public StockVenta() {
        this.cantidadCajas = 1;
        this.detalleCajaStock = new ArrayList<>();
        this.caja = new TipoCaja();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidadCajas() {
        return cantidadCajas;
    }

    public void setCantidadCajas(Integer cantidadCajas) {
        this.cantidadCajas = cantidadCajas;
    }

    public Integer getTotalTallos() {
        return this.CalcularTotalTallos();
    }

    public void setTotalTallos(Integer totalTallos) {
        this.totalTallos = totalTallos;
    }

    public List<ItemVariedadStock> getDetalleCajaStock() {
        return detalleCajaStock;
    }

    public void setDetalleCajaStock(List<ItemVariedadStock> detalleCajaStock) {
        this.detalleCajaStock = detalleCajaStock;
    }

    public TipoCaja getCaja() {
        return caja;
    }

    public void setCaja(TipoCaja caja) {
        this.caja = caja;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getXml() {
        return xml;
    }

    public String getHtml() {
        return html;
    }

    public String getPdf() {
        return pdf;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
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

    private Integer CalcularTotalTallos() {
        Integer total = 0;
        if (this.detalleCajaStock != null && !this.detalleCajaStock.isEmpty()) {
            for (ItemVariedadStock item : this.detalleCajaStock) {
                total = total + item.getTotalTallos();
            }
        }
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final StockVenta other = (StockVenta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockVenta{" + "codigo=" + codigo + ", cantidadCajas=" + cantidadCajas + ", totalTallos=" + totalTallos + ", barcode=" + barcode + ", message=" + message + ", xml=" + xml + ", html=" + html + ", pdf=" + pdf + ", urlPdf=" + urlPdf + ", username=" + username + ", flag=" + flag + ", detalleCajaStock=" + detalleCajaStock + ", caja=" + caja + '}';
    }

}

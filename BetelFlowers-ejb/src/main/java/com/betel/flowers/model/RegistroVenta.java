/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.Date;
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
@Entity(value = "RegistroVenta", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroVenta extends BaseEntity {

    private Integer codigo;
    private Integer numberPaking;
    private Integer numberSRI;
    private Date fechaSRI;
    private String AWB;
    private String HAWB;
    private String observacion;
    private String barcode;
    private String username;
    private Integer flag;

    @Reference
    private Cliente cliente;

    private SubCliente subCliente;
    private Ciudad puertoEmbarque;
    private Ciudad puertoDestino;
    private Dae dae;
    private Carguera agenciaCarga;
    private CuartoFrio cuartoFrio;
    private TerminoExportacion termino;

    public RegistroVenta() {
        this.cliente = new Cliente();
        this.subCliente = new SubCliente();
        this.dae = new Dae();
        this.agenciaCarga = new Carguera();
        this.termino = new TerminoExportacion();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getNumberPaking() {
        return numberPaking;
    }

    public void setNumberPaking(Integer numberPaking) {
        this.numberPaking = numberPaking;
    }

    public Integer getNumberSRI() {
        return numberSRI;
    }

    public void setNumberSRI(Integer numberSRI) {
        this.numberSRI = numberSRI;
    }

    public Date getFechaSRI() {
        return fechaSRI;
    }

    public void setFechaSRI(Date fechaSRI) {
        this.fechaSRI = fechaSRI;
    }

    public String getAWB() {
        return AWB;
    }

    public void setAWB(String AWB) {
        this.AWB = AWB;
    }

    public String getHAWB() {
        return HAWB;
    }

    public void setHAWB(String HAWB) {
        this.HAWB = HAWB;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SubCliente getSubCliente() {
        return subCliente;
    }

    public void setSubCliente(SubCliente subCliente) {
        this.subCliente = subCliente;
    }

    public Ciudad getPuertoEmbarque() {
        return puertoEmbarque;
    }

    public void setPuertoEmbarque(Ciudad puertoEmbarque) {
        this.puertoEmbarque = puertoEmbarque;
    }

    public Ciudad getPuertoDestino() {
        return puertoDestino;
    }

    public void setPuertoDestino(Ciudad puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    public Dae getDae() {
        return dae;
    }

    public void setDae(Dae dae) {
        this.dae = dae;
    }

    public Carguera getAgenciaCarga() {
        return agenciaCarga;
    }

    public void setAgenciaCarga(Carguera agenciaCarga) {
        this.agenciaCarga = agenciaCarga;
    }

    public CuartoFrio getCuartoFrio() {
        return cuartoFrio;
    }

    public void setCuartoFrio(CuartoFrio cuartoFrio) {
        this.cuartoFrio = cuartoFrio;
    }

    public TerminoExportacion getTermino() {
        return termino;
    }

    public void setTermino(TerminoExportacion termino) {
        this.termino = termino;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.codigo);
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
        final RegistroVenta other = (RegistroVenta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroVenta{" + "codigo=" + codigo + ", numberPaking=" + numberPaking + ", numberSRI=" + numberSRI + ", fechaSRI=" + fechaSRI + ", AWB=" + AWB + ", HAWB=" + HAWB + ", observacion=" + observacion + ", barcode=" + barcode + ", username=" + username + ", flag=" + flag + ", cliente=" + cliente + ", subCliente=" + subCliente + ", puertoEmbarque=" + puertoEmbarque + ", puertoDestino=" + puertoDestino + ", dae=" + dae + ", agenciaCarga=" + agenciaCarga + ", cuartoFrio=" + cuartoFrio + ", termino=" + termino + '}';
    }
}

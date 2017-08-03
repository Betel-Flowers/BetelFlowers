/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.ArrayList;
import java.util.Date;
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
@Entity(value = "RegistroVenta", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroVenta extends BaseEntity {

    private Integer codigo;
    private Integer numberPaking;
    private String secuencialSRI;
    private Date fechaSRI;
    private String AWB;
    private String HAWB;
    private String observacion;
    private String barcode;
    private Integer numeroCajas;
    private Double subTotal;
    private String username;//ventas
    private Integer flag;

    @Reference
    private Cliente cliente;
    private Boolean addSubCli;
    
    @Embedded
    private SubCli subcli;

    @Embedded
    private Coordinacion data;

    private Boolean coordinada; //false sin coordinar
    private Boolean empacada; // false sin empacar

    @Embedded
    private List<PointMatrix> matrixVenta;

    public RegistroVenta() {
        this.numberPaking = 0;
        this.secuencialSRI = "";
        this.fechaSRI = new Date();
        this.AWB = "";
        this.HAWB = "";
        this.observacion = "";
        this.barcode = "";
        this.cliente = new Cliente();
        this.addSubCli = Boolean.FALSE;
        this.subcli = new SubCli();
        this.coordinada = Boolean.FALSE;
        this.empacada = Boolean.FALSE;
        this.matrixVenta = new ArrayList<>();
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

    public String getSecuencialSRI() {
        return secuencialSRI;
    }

    public void setSecuencialSRI(String secuencialSRI) {
        this.secuencialSRI = secuencialSRI;
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

    public Integer getNumeroCajas() {
        return numeroCajas;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setNumeroCajas(Integer numeroCajas) {
        this.numeroCajas = numeroCajas;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
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

    public Boolean getAddSubCli() {
        return addSubCli;
    }

    public void setAddSubCli(Boolean addSubCli) {
        this.addSubCli = addSubCli;
    }

    public Coordinacion getData() {
        return data;
    }

    public void setData(Coordinacion data) {
        this.data = data;
    }

    public List<PointMatrix> getMatrixVenta() {
        return matrixVenta;
    }

    public void setMatrixVenta(List<PointMatrix> matrixVenta) {
        this.matrixVenta = matrixVenta;
    }

    public Boolean getCoordinada() {
        return coordinada;
    }

    public void setCoordinada(Boolean coordinada) {
        this.coordinada = coordinada;
    }

    public Boolean getEmpacada() {
        return empacada;
    }

    public void setEmpacada(Boolean empacada) {
        this.empacada = empacada;
    }
    
        public SubCli getSubcli() {
        return subcli;
    }

    public void setSubcli(SubCli subcli) {
        this.subcli = subcli;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final RegistroVenta other = (RegistroVenta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroVenta{" + "codigo=" + codigo + ", numberPaking=" + numberPaking + ", numberSRI=" + secuencialSRI + ", fechaSRI=" + fechaSRI + ", AWB=" + AWB + ", HAWB=" + HAWB + ", observacion=" + observacion + ", barcode=" + barcode + ", username=" + username + ", flag=" + flag + ", cliente=" + cliente + ", data=" + data + ", matrixVenta=" + matrixVenta + '}';
    }
}

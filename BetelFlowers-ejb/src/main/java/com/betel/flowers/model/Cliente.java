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
@Entity(value = "Cliente", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo")),
    @Index(fields = @Field("ruc"))})
public class Cliente extends BaseEntity {

    private String codigo;
    private Boolean comercializadora;
    private Boolean exterior;
    private Boolean local;
    private String nombreContacto;
    private String empresa;
    private String ruc;
    private String direccion;
    private Integer diasPago;
    private Double limiteCredito;
    private Integer diasCredito;
    private Boolean impuestos;
    private String username;
    private Integer flag;

    @Reference
    private Ciudad ciudad;
    @Reference
    private ZonaGeografica zona;
    @Embedded
    private List<Telefono> telefonos;
    @Embedded
    private List<Correo> correos;
    @Reference
    private List<MarcaCaja> cajas;
    @Reference
    private List<Cliente> subClientes;

    public Cliente() {
        this.ciudad = new Ciudad();
        this.zona = new ZonaGeografica();
        this.telefonos = new ArrayList<>();
        this.correos = new ArrayList<>();
        this.cajas = new ArrayList<>();
        this.subClientes = new ArrayList<>();
        this.comercializadora = Boolean.FALSE;
        this.local = Boolean.FALSE;
        this.exterior = Boolean.FALSE;
        this.impuestos = Boolean.FALSE;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getComercializadora() {
        return comercializadora;
    }

    public void setComercializadora(Boolean comercializadora) {
        this.comercializadora = comercializadora;
    }

    public Boolean getExterior() {
        return exterior;
    }

    public void setExterior(Boolean exterior) {
        this.exterior = exterior;
    }

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(Integer diasPago) {
        this.diasPago = diasPago;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public Boolean getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Boolean impuestos) {
        this.impuestos = impuestos;
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

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public ZonaGeografica getZona() {
        return zona;
    }

    public void setZona(ZonaGeografica zona) {
        this.zona = zona;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(List<Correo> correos) {
        this.correos = correos;
    }

    public List<MarcaCaja> getCajas() {
        return cajas;
    }

    public void setCajas(List<MarcaCaja> cajas) {
        this.cajas = cajas;
    }

    public List<Cliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<Cliente> subClientes) {
        this.subClientes = subClientes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.codigo);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codigo=" + codigo + ", comercializadora=" + comercializadora + ", exterior=" + exterior + ", local=" + local + ", nombreContacto=" + nombreContacto + ", empresa=" + empresa + ", ruc=" + ruc + ", direccion=" + direccion + ", diasPago=" + diasPago + ", limiteCredito=" + limiteCredito + ", diasCredito=" + diasCredito + ", impuestos=" + impuestos + ", username=" + username + ", flag=" + flag + ", ciudad=" + ciudad + ", telefonos=" + telefonos + ", correos=" + correos + ", cajas=" + cajas + '}';
    }

}

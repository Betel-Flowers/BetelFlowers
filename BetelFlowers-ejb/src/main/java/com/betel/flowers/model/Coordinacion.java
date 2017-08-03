/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.Date;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class Coordinacion {

    @Reference
    private Ciudad puertoEmbarque;
    @Reference
    private Ciudad puertoDestino;
    @Reference
    private Dae dae;
    @Reference
    private Carguera agenciaCarga;
    @Reference
    private CuartoFrioCarguera cuartoFrio;
    @Reference
    private TerminoExportacion termino;

    private String festividad;
    private Date fechaVuelo;
    private Date fechaCoordinacion;

    private String username; //coordinacion

    public Coordinacion() {
        this.puertoEmbarque = new Ciudad();
        this.puertoDestino = new Ciudad();
        this.dae = new Dae();
        this.agenciaCarga = new Carguera();
        this.cuartoFrio = new CuartoFrioCarguera();
        this.termino = new TerminoExportacion();
        this.festividad = "";
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

    public CuartoFrioCarguera getCuartoFrio() {
        return cuartoFrio;
    }

    public void setCuartoFrio(CuartoFrioCarguera cuartoFrio) {
        this.cuartoFrio = cuartoFrio;
    }

    public TerminoExportacion getTermino() {
        return termino;
    }

    public void setTermino(TerminoExportacion termino) {
        this.termino = termino;
    }

    public String getFestividad() {
        return festividad;
    }

    public void setFestividad(String festividad) {
        this.festividad = festividad;
    }

    public Date getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(Date fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getFechaCoordinacion() {
        return fechaCoordinacion;
    }

    public void setFechaCoordinacion(Date fechaCoordinacion) {
        this.fechaCoordinacion = fechaCoordinacion;
    }

}

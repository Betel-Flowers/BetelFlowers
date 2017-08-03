/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author luis
 */
@Embedded
public class Coordinacion {

    private Ciudad puertoEmbarque;
    private Ciudad puertoDestino;
    private Dae dae;
    private Carguera agenciaCarga;
    private CuartoFrio cuartoFrio;
    private TerminoExportacion termino;

    public Coordinacion() {
        this.puertoEmbarque = new Ciudad();
        this.puertoDestino = new Ciudad();
        this.dae = new Dae();
        this.agenciaCarga = new Carguera();
        this.cuartoFrio = new CuartoFrio();
        this.termino = new TerminoExportacion();
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

}

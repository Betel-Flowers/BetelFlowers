/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class DetalleNacional {

    private Integer index;
    @Reference
    private Causa causa;
    private List<String> motivos;

    public DetalleNacional() {
        this.causa = new Causa();
        this.motivos = new ArrayList<>();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Causa getCausa() {
        return causa;
    }

    public void setCausa(Causa causa) {
        this.causa = causa;
    }

    public List<String> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<String> motivos) {
        this.motivos = motivos;
    }
}

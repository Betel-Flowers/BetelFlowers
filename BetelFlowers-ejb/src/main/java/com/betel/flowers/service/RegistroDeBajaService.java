/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.RegistroDeBaja;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class RegistroDeBajaService implements Serializable {
    
    private static final long serialVersionUID = -5206985302435989572L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    
    public Boolean insert(RegistroDeBaja registroDeBaja) {
        Boolean exito = Boolean.FALSE;
        RegistroDeBaja axu = this.findByCodigo(registroDeBaja);
        if (axu.getId() == null) {
            registroDeBaja.setCodigo(this.obtenerCodigo());
            registroDeBaja.setFlag(1);
            this.ds.save(registroDeBaja);
            exito = Boolean.TRUE;
        }
        return exito;
    }
    
    private Integer obtenerCodigo() {
        List<RegistroDeBaja> registroDeBajas = this.ds.find(RegistroDeBaja.class).asList();
        if (registroDeBajas == null) {
            registroDeBajas = new ArrayList<>();
        }
        Integer size = registroDeBajas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }
    
    public List<RegistroDeBaja> obtenerLista() {
        List<RegistroDeBaja> registroDeBajas = this.ds.find(RegistroDeBaja.class).asList();
        return registroDeBajas;
    }
    
    public List<RegistroDeBaja> obtenerListFlag(Integer flag) {
        List<RegistroDeBaja> list = new ArrayList<>();
        Query<RegistroDeBaja> result = this.ds.find(RegistroDeBaja.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }
    
    public RegistroDeBaja findByCodigo(RegistroDeBaja registroDeBaja) {
        RegistroDeBaja find = new RegistroDeBaja();
        Query<RegistroDeBaja> result = this.ds.find(RegistroDeBaja.class).
                field("codigo").equal(registroDeBaja.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public RegistroDeBaja findByCodigo(Integer registroDeBaja) {
        RegistroDeBaja find = new RegistroDeBaja();
        Query<RegistroDeBaja> result = this.ds.find(RegistroDeBaja.class).
                field("codigo").equal(registroDeBaja).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public RegistroDeBaja findByBarcode(RegistroDeBaja registroDeBaja) {
        RegistroDeBaja find = new RegistroDeBaja();
        Query<RegistroDeBaja> result = this.ds.find(RegistroDeBaja.class).
                field("barcode").equal(registroDeBaja.getBarcode()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public void delete(RegistroDeBaja registroDeBaja) {
        this.ds.delete(registroDeBaja);
    }
    
    public Boolean deteleFlag(RegistroDeBaja registroDeBaja) {
        Query<RegistroDeBaja> query = this.ds.createQuery(RegistroDeBaja.class);
        registroDeBaja.setFlag(0);
        query.and(
                query.criteria("codigo").equal(registroDeBaja.getCodigo())
        );
        UpdateOperations<RegistroDeBaja> update = this.ds.createUpdateOperations(RegistroDeBaja.class);
        update.set("flag", registroDeBaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(RegistroDeBaja registroDeBaja) {
        Query<RegistroDeBaja> query = this.ds.createQuery(RegistroDeBaja.class);
        query.and(
                query.criteria("codigo").equal(registroDeBaja.getCodigo())
        );
        UpdateOperations<RegistroDeBaja> update = this.ds.createUpdateOperations(RegistroDeBaja.class);
        update.set("barcode", registroDeBaja.getBarcode()).
                set("motivo", registroDeBaja.getMotivo()).
                set("contenedor", registroDeBaja.getContenedor()).
                set("username", registroDeBaja.getUsername()).
                set("flag", registroDeBaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

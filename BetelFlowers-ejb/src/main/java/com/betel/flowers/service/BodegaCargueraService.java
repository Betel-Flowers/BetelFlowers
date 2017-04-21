/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.BodegaCarguera;
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
public class BodegaCargueraService implements Serializable{
    
    private static final long serialVersionUID = 170646726473538026L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    
    public Boolean insert(BodegaCarguera bodegaCarguera) {
        Boolean exito = Boolean.FALSE;
        BodegaCarguera axu = this.findByCodigo(bodegaCarguera);
        if (axu.getId() == null) {
            bodegaCarguera.setCodigo(this.obtenerCodigo());
            bodegaCarguera.setFlag(1);
            this.ds.save(bodegaCarguera);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<BodegaCarguera> bodegaCargueras = this.ds.find(BodegaCarguera.class).asList();
        if (bodegaCargueras == null) {
            bodegaCargueras = new ArrayList<>();
        }
        Integer size = bodegaCargueras.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<BodegaCarguera> obtenerLista() {
        List<BodegaCarguera> bodegaCargueras = this.ds.find(BodegaCarguera.class).asList();
        return bodegaCargueras;
    }

    public List<BodegaCarguera> obtenerListFlag(Integer flag) {
        List<BodegaCarguera> list = new ArrayList<>();
        Query<BodegaCarguera> result = this.ds.find(BodegaCarguera.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public BodegaCarguera findByCodigo(BodegaCarguera bodegaCarguera) {
        BodegaCarguera find = new BodegaCarguera();
        Query<BodegaCarguera> result = this.ds.find(BodegaCarguera.class).
                field("codigo").equal(bodegaCarguera.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public BodegaCarguera findByCodigo(Integer bodegaCarguera) {
        BodegaCarguera find = new BodegaCarguera();
        Query<BodegaCarguera> result = this.ds.find(BodegaCarguera.class).
                field("codigo").equal(bodegaCarguera).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(BodegaCarguera bodegaCarguera) {
        this.ds.delete(bodegaCarguera);
    }

    public Boolean deteleFlag(BodegaCarguera bodegaCarguera) {
        Query<BodegaCarguera> query = this.ds.createQuery(BodegaCarguera.class);
        bodegaCarguera.setFlag(0);
        query.and(
                query.criteria("codigo").equal(bodegaCarguera.getCodigo())
        );
        UpdateOperations<BodegaCarguera> update = this.ds.createUpdateOperations(BodegaCarguera.class);
        update.set("flag", bodegaCarguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(BodegaCarguera bodegaCarguera) {
        Query<BodegaCarguera> query = this.ds.createQuery(BodegaCarguera.class);
        query.and(
                query.criteria("codigo").equal(bodegaCarguera.getCodigo())
        );
        UpdateOperations<BodegaCarguera> update = this.ds.createUpdateOperations(BodegaCarguera.class);
        update.set("nombre", bodegaCarguera.getNombre()).
                set("username", bodegaCarguera.getUsername()).
                set("flag", bodegaCarguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

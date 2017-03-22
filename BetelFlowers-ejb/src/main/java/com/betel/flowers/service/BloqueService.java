/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Bloque;
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
public class BloqueService implements Serializable {

    private static final long serialVersionUID = 563549719444755084L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Bloque bloque) {
        Boolean exito = Boolean.FALSE;
        Bloque axu = this.findByCodigo(bloque);
        if (axu.getId() == null) {
            bloque.setCodigo(this.obtenerCodigo());
            bloque.setFlag(1);
            this.ds.save(bloque);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Bloque> bloques = this.ds.find(Bloque.class).asList();
        if (bloques == null) {
            bloques = new ArrayList<>();
        }
        Integer size = bloques.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Bloque> obtenerLista() {
        List<Bloque> bloques = this.ds.find(Bloque.class).asList();
        return bloques;
    }

    public List<Bloque> obtenerListFlag(Integer flag) {
        List<Bloque> list = new ArrayList<>();
        Query<Bloque> result = this.ds.find(Bloque.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Bloque findByCodigo(Bloque bloque) {
        Bloque find = new Bloque();
        Query<Bloque> result = this.ds.find(Bloque.class).
                field("codigo").equal(bloque.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Bloque findByCodigo(Integer bloque) {
        Bloque find = new Bloque();
        Query<Bloque> result = this.ds.find(Bloque.class).
                field("codigo").equal(bloque).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Bloque bloque) {
        this.ds.delete(bloque);
    }

    public Boolean deteleFlag(Bloque bloque) {
        Query<Bloque> query = this.ds.createQuery(Bloque.class);
        bloque.setFlag(0);
        query.and(
                query.criteria("codigo").equal(bloque.getCodigo())
        );
        UpdateOperations<Bloque> update = this.ds.createUpdateOperations(Bloque.class);
        update.set("flag", bloque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(Bloque bloque) {
        Query<Bloque> query = this.ds.createQuery(Bloque.class);
        query.and(
                query.criteria("codigo").equal(bloque.getCodigo())
        );
        UpdateOperations<Bloque> update = this.ds.createUpdateOperations(Bloque.class);
        update.set("descripcion", bloque.getDescripcion()).
                set("ubicacion", bloque.getUbicacion()).
                set("area", bloque.getArea()).
                set("flag", bloque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

}

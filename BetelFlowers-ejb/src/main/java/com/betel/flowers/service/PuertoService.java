/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Puerto;
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
public class PuertoService implements Serializable {

    private static final long serialVersionUID = -9056502864649693309L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Puerto puerto) {
        Boolean exito = Boolean.FALSE;
        Puerto axu = this.findByCodigo(puerto);
        if (axu.getId() == null) {
            puerto.setCodigo(this.obtenerCodigo());
            puerto.setFlag(1);
            this.ds.save(puerto);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Puerto> puertos = this.ds.find(Puerto.class).asList();
        if (puertos == null) {
            puertos = new ArrayList<>();
        }
        Integer size = puertos.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Puerto> obtenerLista() {
        List<Puerto> puertos = this.ds.find(Puerto.class).asList();
        return puertos;
    }

    public List<Puerto> obtenerListFlag(Integer flag) {
        List<Puerto> list = new ArrayList<>();
        Query<Puerto> result = this.ds.find(Puerto.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Puerto findByCodigo(Puerto puerto) {
        Puerto find = new Puerto();
        Query<Puerto> result = this.ds.find(Puerto.class).
                field("codigo").equal(puerto.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Puerto findByCodigo(Integer puerto) {
        Puerto find = new Puerto();
        Query<Puerto> result = this.ds.find(Puerto.class).
                field("codigo").equal(puerto).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Puerto puerto) {
        this.ds.delete(puerto);
    }

    public Boolean deteleFlag(Puerto puerto) {
        Query<Puerto> query = this.ds.createQuery(Puerto.class);
        puerto.setFlag(0);
        query.and(
                query.criteria("codigo").equal(puerto.getCodigo())
        );
        UpdateOperations<Puerto> update = this.ds.createUpdateOperations(Puerto.class);
        update.set("flag", puerto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(Puerto puerto) {
        Query<Puerto> query = this.ds.createQuery(Puerto.class);
        query.and(
                query.criteria("codigo").equal(puerto.getCodigo())
        );
        UpdateOperations<Puerto> update = this.ds.createUpdateOperations(Puerto.class);
        update.set("nombre", puerto.getNombre()).
                set("ciudad", puerto.getCiudad()).
                set("flag", puerto.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

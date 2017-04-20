/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Causa;
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
public class CausaService implements Serializable {

    private static final long serialVersionUID = -2604392751281876846L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Causa causa) {
        Boolean exito = Boolean.FALSE;
        Causa axu = this.findByCodigo(causa);
        if (axu.getId() == null) {
            causa.setCodigo(this.obtenerCodigo());
            causa.setFlag(1);
            this.ds.save(causa);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Causa> causas = this.ds.find(Causa.class).asList();
        if (causas == null) {
            causas = new ArrayList<>();
        }
        Integer size = causas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Causa> obtenerLista() {
        List<Causa> causas = this.ds.find(Causa.class).asList();
        return causas;
    }

    public List<Causa> obtenerListFlag(Integer flag) {
        List<Causa> list = new ArrayList<>();
        Query<Causa> result = this.ds.find(Causa.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Causa findByCodigo(Causa causa) {
        Causa find = new Causa();
        Query<Causa> result = this.ds.find(Causa.class).
                field("codigo").equal(causa.getCodigo()).
                field("flag").equal(1);;
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Causa findByCodigo(Integer causa) {
        Causa find = new Causa();
        Query<Causa> result = this.ds.find(Causa.class).
                field("codigo").equal(causa).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Causa causa) {
        this.ds.delete(causa);
    }

    public Boolean deteleFlag(Causa causa) {
        Query<Causa> query = this.ds.createQuery(Causa.class);
        causa.setFlag(0);
        query.and(
                query.criteria("codigo").equal(causa.getCodigo())
        );
        UpdateOperations<Causa> update = this.ds.createUpdateOperations(Causa.class);
        update.set("flag", causa.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Causa causa) {
        Query<Causa> query = this.ds.createQuery(Causa.class);
        query.and(
                query.criteria("codigo").equal(causa.getCodigo())
        );
        UpdateOperations<Causa> update = this.ds.createUpdateOperations(Causa.class);
        update.set("descripcion", causa.getDescripcion()).
                set("username", causa.getUsername()).
                set("flag", causa.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

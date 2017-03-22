/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.BodegaVirtual;
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
public class BodegaVirtualService implements Serializable {

    private static final long serialVersionUID = -7822372906118736448L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(BodegaVirtual bodegaVirtual) {
        Boolean exito = Boolean.FALSE;
        BodegaVirtual axu = this.findByCodigo(bodegaVirtual);
        if (axu.getId() == null) {
            bodegaVirtual.setCodigo(this.obtenerCodigo());
            bodegaVirtual.setFlag(1);
            this.ds.save(bodegaVirtual);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<BodegaVirtual> bodegaVirtuals = this.ds.find(BodegaVirtual.class).asList();
        if (bodegaVirtuals == null) {
            bodegaVirtuals = new ArrayList<>();
        }
        Integer size = bodegaVirtuals.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<BodegaVirtual> obtenerLista() {
        List<BodegaVirtual> bodegaVirtuals = this.ds.find(BodegaVirtual.class).asList();
        return bodegaVirtuals;
    }

    public List<BodegaVirtual> obtenerListFlag(Integer flag) {
        List<BodegaVirtual> list = new ArrayList<>();
        Query<BodegaVirtual> result = this.ds.find(BodegaVirtual.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public BodegaVirtual findByCodigo(BodegaVirtual bodegaVirtual) {
        BodegaVirtual find = new BodegaVirtual();
        Query<BodegaVirtual> result = this.ds.find(BodegaVirtual.class).
                field("codigo").equal(bodegaVirtual.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public BodegaVirtual findByCodigo(Integer bodegaVirtual) {
        BodegaVirtual find = new BodegaVirtual();
        Query<BodegaVirtual> result = this.ds.find(BodegaVirtual.class).
                field("codigo").equal(bodegaVirtual).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(BodegaVirtual bodegaVirtual) {
        this.ds.delete(bodegaVirtual);
    }

    public Boolean deteleFlag(BodegaVirtual bodegaVirtual) {
        Query<BodegaVirtual> query = this.ds.createQuery(BodegaVirtual.class);
        bodegaVirtual.setFlag(0);
        query.and(
                query.criteria("codigo").equal(bodegaVirtual.getCodigo())
        );
        UpdateOperations<BodegaVirtual> update = this.ds.createUpdateOperations(BodegaVirtual.class);
        update.set("flag", bodegaVirtual.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(BodegaVirtual bodegaVirtual) {
        Query<BodegaVirtual> query = this.ds.createQuery(BodegaVirtual.class);
        query.and(
                query.criteria("codigo").equal(bodegaVirtual.getCodigo())
        );
        UpdateOperations<BodegaVirtual> update = this.ds.createUpdateOperations(BodegaVirtual.class);
        update.set("nombre", bodegaVirtual.getNombre()).
                set("cuartoFrio", bodegaVirtual.getCuartoFrio()).
                set("username", bodegaVirtual.getUsername()).
                set("flag", bodegaVirtual.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

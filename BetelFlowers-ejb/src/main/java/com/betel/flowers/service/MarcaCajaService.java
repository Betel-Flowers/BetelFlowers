/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.MarcaCaja;
import com.betel.flowers.model.Pais;
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
public class MarcaCajaService implements Serializable {

    private static final long serialVersionUID = 3512846109491587993L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(MarcaCaja marcaCaja) {
        Boolean exito = Boolean.FALSE;
        MarcaCaja axu = this.findByCodigo(marcaCaja);
        if (axu.getId() == null) {
            marcaCaja.setCodigo(this.obtenerCodigo());
            marcaCaja.setFlag(1);
            this.ds.save(marcaCaja);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<MarcaCaja> marcaCajas = this.ds.find(MarcaCaja.class).asList();
        if (marcaCajas == null) {
            marcaCajas = new ArrayList<>();
        }
        Integer size = marcaCajas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<MarcaCaja> obtenerLista() {
        List<MarcaCaja> marcaCajas = this.ds.find(MarcaCaja.class).asList();
        return marcaCajas;
    }

    public List<MarcaCaja> obtenerListFlag(Integer flag) {
        List<MarcaCaja> list = new ArrayList<>();
        Query<MarcaCaja> result = this.ds.find(MarcaCaja.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public MarcaCaja findByCodigo(MarcaCaja marcaCaja) {
        MarcaCaja find = new MarcaCaja();
        Query<MarcaCaja> result = this.ds.find(MarcaCaja.class).
                field("codigo").equal(marcaCaja.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public MarcaCaja findByCodigo(Integer marcaCaja) {
        MarcaCaja find = new MarcaCaja();
        Query<MarcaCaja> result = this.ds.find(MarcaCaja.class).
                field("codigo").equal(marcaCaja).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(MarcaCaja marcaCaja) {
        this.ds.delete(marcaCaja);
    }

    public Boolean deteleFlag(MarcaCaja marcaCaja) {
        Query<MarcaCaja> query = this.ds.createQuery(MarcaCaja.class);
        marcaCaja.setFlag(0);
        query.and(
                query.criteria("codigo").equal(marcaCaja.getCodigo())
        );
        UpdateOperations<MarcaCaja> update = this.ds.createUpdateOperations(MarcaCaja.class);
        update.set("flag", marcaCaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(MarcaCaja marcaCaja) {
        Query<MarcaCaja> query = this.ds.createQuery(MarcaCaja.class);
        query.and(
                query.criteria("codigo").equal(marcaCaja.getCodigo())
        );
        UpdateOperations<MarcaCaja> update = this.ds.createUpdateOperations(MarcaCaja.class);
        update.set("nombre", marcaCaja.getNombre()).
                set("username", marcaCaja.getUsername()).
                set("flag", marcaCaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

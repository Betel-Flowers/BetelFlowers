/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.TipoCaja;
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
public class TipoCajaService implements Serializable {

    private static final long serialVersionUID = -3263657749246207909L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(TipoCaja tipoCaja) {
        Boolean exito = Boolean.FALSE;
        TipoCaja axu = this.findByCodigo(tipoCaja);
        if (axu.getId() == null) {
            tipoCaja.setCodigo(this.obtenerCodigo());
            tipoCaja.setFlag(1);
            this.ds.save(tipoCaja);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<TipoCaja> tipoCajas = this.ds.find(TipoCaja.class).asList();
        if (tipoCajas == null) {
            tipoCajas = new ArrayList<>();
        }
        Integer size = tipoCajas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<TipoCaja> obtenerLista() {
        List<TipoCaja> tipoCajas = this.ds.find(TipoCaja.class).asList();
        return tipoCajas;
    }

    public List<TipoCaja> obtenerListFlag(Integer flag) {
        List<TipoCaja> list = new ArrayList<>();
        Query<TipoCaja> result = this.ds.find(TipoCaja.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public TipoCaja findByCodigo(TipoCaja tipoCaja) {
        TipoCaja find = new TipoCaja();
        Query<TipoCaja> result = this.ds.find(TipoCaja.class).
                field("codigo").equal(tipoCaja.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public TipoCaja findByCodigo(Integer tipoCaja) {
        TipoCaja find = new TipoCaja();
        Query<TipoCaja> result = this.ds.find(TipoCaja.class).
                field("codigo").equal(tipoCaja).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(TipoCaja tipoCaja) {
        this.ds.delete(tipoCaja);
    }

    public Boolean deteleFlag(TipoCaja tipoCaja) {
        Query<TipoCaja> query = this.ds.createQuery(TipoCaja.class);
        tipoCaja.setFlag(0);
        query.and(
                query.criteria("codigo").equal(tipoCaja.getCodigo())
        );
        UpdateOperations<TipoCaja> update = this.ds.createUpdateOperations(TipoCaja.class);
        update.set("flag", tipoCaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(TipoCaja tipoCaja) {
        Query<TipoCaja> query = this.ds.createQuery(TipoCaja.class);
        query.and(
                query.criteria("codigo").equal(tipoCaja.getCodigo())
        );
        UpdateOperations<TipoCaja> update = this.ds.createUpdateOperations(TipoCaja.class);
        update.set("tipo", tipoCaja.getTipo()).
                set("valor", tipoCaja.getValor()).
                set("username", tipoCaja.getUsername()).
                set("flag", tipoCaja.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

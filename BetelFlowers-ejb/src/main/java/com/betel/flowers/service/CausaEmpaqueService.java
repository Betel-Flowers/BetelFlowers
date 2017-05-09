/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.CausaEmpaque;
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
public class CausaEmpaqueService implements Serializable{
    
    private static final long serialVersionUID = 2972025345995476830L;
    
     private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(CausaEmpaque causaEmpaque) {
        Boolean exito = Boolean.FALSE;
        CausaEmpaque axu = this.findByCodigo(causaEmpaque);
        if (axu.getId() == null) {
            causaEmpaque.setCodigo(this.obtenerCodigo());
            causaEmpaque.setFlag(1);
            this.ds.save(causaEmpaque);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<CausaEmpaque> causaEmpaques = this.ds.find(CausaEmpaque.class).asList();
        if (causaEmpaques == null) {
            causaEmpaques = new ArrayList<>();
        }
        Integer size = causaEmpaques.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<CausaEmpaque> obtenerLista() {
        List<CausaEmpaque> causaEmpaques = this.ds.find(CausaEmpaque.class).asList();
        return causaEmpaques;
    }

    public List<CausaEmpaque> obtenerListFlag(Integer flag) {
        List<CausaEmpaque> list = new ArrayList<>();
        Query<CausaEmpaque> result = this.ds.find(CausaEmpaque.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public CausaEmpaque findByCodigo(CausaEmpaque causaEmpaque) {
        CausaEmpaque find = new CausaEmpaque();
        Query<CausaEmpaque> result = this.ds.find(CausaEmpaque.class).
                field("codigo").equal(causaEmpaque.getCodigo()).
                field("flag").equal(1);;
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public CausaEmpaque findByCodigo(Integer causaEmpaque) {
        CausaEmpaque find = new CausaEmpaque();
        Query<CausaEmpaque> result = this.ds.find(CausaEmpaque.class).
                field("codigo").equal(causaEmpaque).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(CausaEmpaque causaEmpaque) {
        this.ds.delete(causaEmpaque);
    }

    public Boolean deteleFlag(CausaEmpaque causaEmpaque) {
        Query<CausaEmpaque> query = this.ds.createQuery(CausaEmpaque.class);
        causaEmpaque.setFlag(0);
        query.and(
                query.criteria("codigo").equal(causaEmpaque.getCodigo())
        );
        UpdateOperations<CausaEmpaque> update = this.ds.createUpdateOperations(CausaEmpaque.class);
        update.set("flag", causaEmpaque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(CausaEmpaque causaEmpaque) {
        Query<CausaEmpaque> query = this.ds.createQuery(CausaEmpaque.class);
        query.and(
                query.criteria("codigo").equal(causaEmpaque.getCodigo())
        );
        UpdateOperations<CausaEmpaque> update = this.ds.createUpdateOperations(CausaEmpaque.class);
        update.set("descripcion", causaEmpaque.getDescripcion()).
                set("username", causaEmpaque.getUsername()).
                set("flag", causaEmpaque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

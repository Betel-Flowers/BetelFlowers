/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.CuartoFrio;
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
public class CuartoFrioService implements Serializable {

    private static final long serialVersionUID = 1887596156318636198L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(CuartoFrio cuartoFrio) {
        Boolean exito = Boolean.FALSE;
        CuartoFrio axu = this.findByCodigo(cuartoFrio);
        if (axu.getId() == null) {
            cuartoFrio.setCodigo(this.obtenerCodigo());
            cuartoFrio.setFlag(1);
            this.ds.save(cuartoFrio);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<CuartoFrio> cuartoFrios = this.ds.find(CuartoFrio.class).asList();
        if (cuartoFrios == null) {
            cuartoFrios = new ArrayList<>();
        }
        Integer size = cuartoFrios.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<CuartoFrio> obtenerLista() {
        List<CuartoFrio> cuartoFrios = this.ds.find(CuartoFrio.class).asList();
        return cuartoFrios;
    }

    public List<CuartoFrio> obtenerListFlag(Integer flag) {
        List<CuartoFrio> list = new ArrayList<>();
        Query<CuartoFrio> result = this.ds.find(CuartoFrio.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public CuartoFrio findByCodigo(CuartoFrio cuartoFrio) {
        CuartoFrio find = new CuartoFrio();
        Query<CuartoFrio> result = this.ds.find(CuartoFrio.class).
                field("codigo").equal(cuartoFrio.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public CuartoFrio findByCodigo(Integer cuartoFrio) {
        CuartoFrio find = new CuartoFrio();
        Query<CuartoFrio> result = this.ds.find(CuartoFrio.class).
                field("codigo").equal(cuartoFrio).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    

    public void delete(CuartoFrio cuartoFrio) {
        this.ds.delete(cuartoFrio);
    }

    public Boolean deteleFlag(CuartoFrio cuartoFrio) {
        Query<CuartoFrio> query = this.ds.createQuery(CuartoFrio.class);
        cuartoFrio.setFlag(0);
        query.and(
                query.criteria("codigo").equal(cuartoFrio.getCodigo())
        );
        UpdateOperations<CuartoFrio> update = this.ds.createUpdateOperations(CuartoFrio.class);
        update.set("flag", cuartoFrio.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(CuartoFrio cuartoFrio) {
        Query<CuartoFrio> query = this.ds.createQuery(CuartoFrio.class);
        query.and(
                query.criteria("codigo").equal(cuartoFrio.getCodigo())
        );
        UpdateOperations<CuartoFrio> update = this.ds.createUpdateOperations(CuartoFrio.class);
        update.set("descripcion", cuartoFrio.getDescripcion()).
                set("ubicacion", cuartoFrio.getUbicacion()).
                set("username", cuartoFrio.getUsername()).
                set("flag", cuartoFrio.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

}

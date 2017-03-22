/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Ciudad;
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
public class CiudadService implements Serializable {

    private static final long serialVersionUID = 9131283055111373731L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Ciudad ciudad) {
        Boolean exito = Boolean.FALSE;
        Ciudad axu = this.findByCodigo(ciudad);
        if (axu.getId() == null) {
            ciudad.setCodigo(this.obtenerCodigo());
            ciudad.setFlag(1);
            this.ds.save(ciudad);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Ciudad> ciudads = this.ds.find(Ciudad.class).asList();
        if (ciudads == null) {
            ciudads = new ArrayList<>();
        }
        Integer size = ciudads.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Ciudad> obtenerLista() {
        List<Ciudad> ciudads = this.ds.find(Ciudad.class).asList();
        return ciudads;
    }

    public List<Ciudad> obtenerListFlag(Integer flag) {
        List<Ciudad> list = new ArrayList<>();
        Query<Ciudad> result = this.ds.find(Ciudad.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }
    
    public List<Ciudad> obtenerListPais(Pais pais) {
        List<Ciudad> list = new ArrayList<>();
        Query<Ciudad> result = this.ds.find(Ciudad.class).
                field("pais").equal(pais).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Ciudad findByCodigo(Ciudad ciudad) {
        Ciudad find = new Ciudad();
        Query<Ciudad> result = this.ds.find(Ciudad.class).
                field("codigo").equal(ciudad.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Ciudad findByCodigo(Integer ciudad) {
        Ciudad find = new Ciudad();
        Query<Ciudad> result = this.ds.find(Ciudad.class).
                field("codigo").equal(ciudad).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Ciudad ciudad) {
        this.ds.delete(ciudad);
    }

    public Boolean deteleFlag(Ciudad ciudad) {
        Query<Ciudad> query = this.ds.createQuery(Ciudad.class);
        ciudad.setFlag(0);
        query.and(
                query.criteria("codigo").equal(ciudad.getCodigo())
        );
        UpdateOperations<Ciudad> update = this.ds.createUpdateOperations(Ciudad.class);
        update.set("flag", ciudad.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Ciudad ciudad) {
        Query<Ciudad> query = this.ds.createQuery(Ciudad.class);
        query.and(
                query.criteria("codigo").equal(ciudad.getCodigo())
        );
        UpdateOperations<Ciudad> update = this.ds.createUpdateOperations(Ciudad.class);
        update.set("nombre", ciudad.getNombre()).
                set("pais", ciudad.getPais()).
                set("flag", ciudad.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

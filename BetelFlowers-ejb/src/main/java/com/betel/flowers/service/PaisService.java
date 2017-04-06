/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

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
public class PaisService implements Serializable{
    
    private static final long serialVersionUID = -2868442755951500830L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Pais pais) {
        Boolean exito = Boolean.FALSE;
        Pais axu = this.findByCodigo(pais);
        if (axu.getId() == null) {
            pais.setCodigo(this.obtenerCodigo());
            pais.setFlag(1);
            this.ds.save(pais);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Pais> paiss = this.ds.find(Pais.class).asList();
        if (paiss == null) {
            paiss = new ArrayList<>();
        }
        Integer size = paiss.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Pais> obtenerLista() {
        List<Pais> paiss = this.ds.find(Pais.class).asList();
        return paiss;
    }

    public List<Pais> obtenerListFlag(Integer flag) {
        List<Pais> list = new ArrayList<>();
        Query<Pais> result = this.ds.find(Pais.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Pais findByCodigo(Pais pais) {
        Pais find = new Pais();
        Query<Pais> result = this.ds.find(Pais.class).
                field("codigo").equal(pais.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Pais findByCodigo(Integer pais) {
        Pais find = new Pais();
        Query<Pais> result = this.ds.find(Pais.class).
                field("codigo").equal(pais).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Pais pais) {
        this.ds.delete(pais);
    }

    public Boolean deteleFlag(Pais pais) {
        Query<Pais> query = this.ds.createQuery(Pais.class);
        pais.setFlag(0);
        query.and(
                query.criteria("codigo").equal(pais.getCodigo())
        );
        UpdateOperations<Pais> update = this.ds.createUpdateOperations(Pais.class);
        update.set("flag", pais.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(Pais pais) {
        Query<Pais> query = this.ds.createQuery(Pais.class);
        query.and(
                query.criteria("codigo").equal(pais.getCodigo())
        );
        UpdateOperations<Pais> update = this.ds.createUpdateOperations(Pais.class);
        update.set("nombre", pais.getNombre()).
                set("codigoPais", pais.getCodigoPais()).
                set("flag", pais.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

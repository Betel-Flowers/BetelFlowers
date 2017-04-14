/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Especie;
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
public class EspecieService implements Serializable {

    private static final long serialVersionUID = -7507106312296847864L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Especie especie) {
        Boolean exito = Boolean.FALSE;
        Especie axu = this.findByCodigo(especie);
        if (axu.getId() == null) {
            especie.setCodigo(this.obtenerCodigo());
            especie.setFlag(1);
            this.ds.save(especie);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public void addVariedad(Especie especie) {
        if (especie != null) {
            this.ds.save(especie);
        }
    }

    private String obtenerCodigo() {
        String Codigo = "BETEL-E";
        List<Especie> especies = this.ds.find(Especie.class).asList();
        if (especies == null) {
            especies = new ArrayList<>();
        }
        Integer size = especies.size();
        Integer number = 1000 + 1 * size;
        return Codigo + number;
    }

    public List<Especie> obtenerLista() {
        List<Especie> especies = this.ds.find(Especie.class).asList();
        return especies;
    }

    public List<Especie> obtenerListFlag(Integer flag) {
        List<Especie> list = new ArrayList<>();
        Query<Especie> result = this.ds.find(Especie.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Especie findByCodigo(Especie especie) {
        Especie find = new Especie();
        Query<Especie> result = this.ds.find(Especie.class).
                field("codigo").equal(especie.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Especie findByCodigo(String especie) {
        Especie find = new Especie();
        Query<Especie> result = this.ds.find(Especie.class).
                field("codigo").equal(especie).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }


    public void delete(Especie especie) {
        this.ds.delete(especie);
    }

    public Boolean deteleFlag(Especie especie) {
        Query<Especie> query = this.ds.createQuery(Especie.class);
        especie.setFlag(0);
        query.and(
                query.criteria("codigo").equal(especie.getCodigo())
        );
        UpdateOperations<Especie> update = this.ds.createUpdateOperations(Especie.class);
        update.set("flag", especie.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Especie especie) {
        Query<Especie> query = this.ds.createQuery(Especie.class);
        query.and(
                query.criteria("codigo").equal(especie.getCodigo())
        );
        UpdateOperations<Especie> update = this.ds.createUpdateOperations(Especie.class);
        update.set("nombre", especie.getNombre()).
                set("codAduana", especie.getCodAduana()).
                set("codNandina", especie.getCodNandina()).
                set("username", especie.getUsername()).
                set("flag", especie.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.ZonaGeografica;
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
public class ZonaGeograficaService implements Serializable{
    
    private static final long serialVersionUID = 3069757342589722425L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(ZonaGeografica zonaGeografica) {
        Boolean exito = Boolean.FALSE;
        ZonaGeografica axu = this.findByCodigo(zonaGeografica);
        if (axu.getId() == null) {
            zonaGeografica.setCodigo(this.obtenerCodigo());
            zonaGeografica.setFlag(1);
            this.ds.save(zonaGeografica);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<ZonaGeografica> zonaGeograficas = this.ds.find(ZonaGeografica.class).asList();
        if (zonaGeograficas == null) {
            zonaGeograficas = new ArrayList<>();
        }
        Integer size = zonaGeograficas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<ZonaGeografica> obtenerLista() {
        List<ZonaGeografica> zonaGeograficas = this.ds.find(ZonaGeografica.class).asList();
        return zonaGeograficas;
    }

    public List<ZonaGeografica> obtenerListFlag(Integer flag) {
        List<ZonaGeografica> list = new ArrayList<>();
        Query<ZonaGeografica> result = this.ds.find(ZonaGeografica.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public ZonaGeografica findByCodigo(ZonaGeografica zonaGeografica) {
        ZonaGeografica find = new ZonaGeografica();
        Query<ZonaGeografica> result = this.ds.find(ZonaGeografica.class).
                field("codigo").equal(zonaGeografica.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public ZonaGeografica findByCodigo(Integer zonaGeografica) {
        ZonaGeografica find = new ZonaGeografica();
        Query<ZonaGeografica> result = this.ds.find(ZonaGeografica.class).
                field("codigo").equal(zonaGeografica).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(ZonaGeografica zonaGeografica) {
        this.ds.delete(zonaGeografica);
    }

    public Boolean deteleFlag(ZonaGeografica zonaGeografica) {
        Query<ZonaGeografica> query = this.ds.createQuery(ZonaGeografica.class);
        zonaGeografica.setFlag(0);
        query.and(
                query.criteria("codigo").equal(zonaGeografica.getCodigo())
        );
        UpdateOperations<ZonaGeografica> update = this.ds.createUpdateOperations(ZonaGeografica.class);
        update.set("flag", zonaGeografica.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(ZonaGeografica zonaGeografica) {
        Query<ZonaGeografica> query = this.ds.createQuery(ZonaGeografica.class);
        query.and(
                query.criteria("codigo").equal(zonaGeografica.getCodigo())
        );
        UpdateOperations<ZonaGeografica> update = this.ds.createUpdateOperations(ZonaGeografica.class);
        update.set("nombre", zonaGeografica.getNombre()).
                set("username", zonaGeografica.getUsername()).
                set("flag", zonaGeografica.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Dae;
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
public class DaeService implements Serializable{
    
    private static final long serialVersionUID = 5842742187640780562L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Dae dae) {
        Boolean exito = Boolean.FALSE;
        Dae axu = this.findByCodigo(dae);
        if (axu.getId() == null) {
            dae.setCodigo(this.obtenerCodigo());
            dae.setFlag(1);
            this.ds.save(dae);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Dae> daes = this.ds.find(Dae.class).asList();
        if (daes == null) {
            daes = new ArrayList<>();
        }
        Integer size = daes.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Dae> obtenerLista() {
        List<Dae> daes = this.ds.find(Dae.class).asList();
        return daes;
    }

    public List<Dae> obtenerListFlag(Integer flag) {
        List<Dae> list = new ArrayList<>();
        Query<Dae> result = this.ds.find(Dae.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Dae findByCodigo(Dae dae) {
        Dae find = new Dae();
        Query<Dae> result = this.ds.find(Dae.class).
                field("codigo").equal(dae.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Dae findByCodigo(Integer dae) {
        Dae find = new Dae();
        Query<Dae> result = this.ds.find(Dae.class).
                field("codigo").equal(dae).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Dae dae) {
        this.ds.delete(dae);
    }

    public Boolean deteleFlag(Dae dae) {
        Query<Dae> query = this.ds.createQuery(Dae.class);
        dae.setFlag(0);
        query.and(
                query.criteria("codigo").equal(dae.getCodigo())
        );
        UpdateOperations<Dae> update = this.ds.createUpdateOperations(Dae.class);
        update.set("flag", dae.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Dae dae) {
        Query<Dae> query = this.ds.createQuery(Dae.class);
        query.and(
                query.criteria("codigo").equal(dae.getCodigo())
        );
        UpdateOperations<Dae> update = this.ds.createUpdateOperations(Dae.class);
        update.set("fechaApertura", dae.getFechaApertura()).
                set("fechaCaducidad", dae.getFechaCaducidad()).
                set("codigoDAE", dae.getCodigoDAE()).
                set("pais", dae.getPais()).
                set("username", dae.getUsername()).
                set("flag", dae.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

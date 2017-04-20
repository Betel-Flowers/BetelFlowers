/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.TipoTrabajo;
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
public class TipoTrabajoService implements Serializable {

    private static final long serialVersionUID = -4636719960077208277L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(TipoTrabajo tipoTrabajo) {
        Boolean exito = Boolean.FALSE;
        TipoTrabajo axu = this.findByCodigo(tipoTrabajo);
        if (axu.getId() == null) {
            tipoTrabajo.setCodigo(this.obtenerCodigo());
            tipoTrabajo.setFlag(1);
            this.ds.save(tipoTrabajo);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<TipoTrabajo> tipoTrabajos = this.ds.find(TipoTrabajo.class).asList();
        if (tipoTrabajos == null) {
            tipoTrabajos = new ArrayList<>();
        }
        Integer size = tipoTrabajos.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<TipoTrabajo> obtenerLista() {
        List<TipoTrabajo> tipoTrabajos = this.ds.find(TipoTrabajo.class).asList();
        return tipoTrabajos;
    }

    public List<TipoTrabajo> obtenerListFlag(Integer flag) {
        List<TipoTrabajo> list = new ArrayList<>();
        Query<TipoTrabajo> result = this.ds.find(TipoTrabajo.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public TipoTrabajo findByCodigo(TipoTrabajo tipoTrabajo) {
        TipoTrabajo find = new TipoTrabajo();
        Query<TipoTrabajo> result = this.ds.find(TipoTrabajo.class).
                field("codigo").equal(tipoTrabajo.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public TipoTrabajo findByCodigo(Integer tipoTrabajo) {
        TipoTrabajo find = new TipoTrabajo();
        Query<TipoTrabajo> result = this.ds.find(TipoTrabajo.class).
                field("codigo").equal(tipoTrabajo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(TipoTrabajo tipoTrabajo) {
        this.ds.delete(tipoTrabajo);
    }

    public Boolean deteleFlag(TipoTrabajo tipoTrabajo) {
        Query<TipoTrabajo> query = this.ds.createQuery(TipoTrabajo.class);
        tipoTrabajo.setFlag(0);
        query.and(
                query.criteria("codigo").equal(tipoTrabajo.getCodigo())
        );
        UpdateOperations<TipoTrabajo> update = this.ds.createUpdateOperations(TipoTrabajo.class);
        update.set("flag", tipoTrabajo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(TipoTrabajo tipoTrabajo) {
        Query<TipoTrabajo> query = this.ds.createQuery(TipoTrabajo.class);
        query.and(
                query.criteria("codigo").equal(tipoTrabajo.getCodigo())
        );
        UpdateOperations<TipoTrabajo> update = this.ds.createUpdateOperations(TipoTrabajo.class);
        update.set("nombre", tipoTrabajo.getNombre()).
                set("username", tipoTrabajo.getUsername()).
                set("flag", tipoTrabajo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

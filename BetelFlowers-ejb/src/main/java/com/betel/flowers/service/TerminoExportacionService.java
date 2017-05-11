/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.TerminoExportacion;
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
public class TerminoExportacionService implements Serializable {
    
    private static final long serialVersionUID = 9168263579489886532L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(TerminoExportacion terminoExportacion) {
        Boolean exito = Boolean.FALSE;
        TerminoExportacion axu = this.findByCodigo(terminoExportacion);
        if (axu.getId() == null) {
            terminoExportacion.setCodigo(this.obtenerCodigo());
            terminoExportacion.setFlag(1);
            this.ds.save(terminoExportacion);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<TerminoExportacion> terminoExportacions = this.ds.find(TerminoExportacion.class).asList();
        if (terminoExportacions == null) {
            terminoExportacions = new ArrayList<>();
        }
        Integer size = terminoExportacions.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<TerminoExportacion> obtenerLista() {
        List<TerminoExportacion> terminoExportacions = this.ds.find(TerminoExportacion.class).asList();
        return terminoExportacions;
    }

    public List<TerminoExportacion> obtenerListFlag(Integer flag) {
        List<TerminoExportacion> list = new ArrayList<>();
        Query<TerminoExportacion> result = this.ds.find(TerminoExportacion.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public TerminoExportacion findByCodigo(TerminoExportacion terminoExportacion) {
        TerminoExportacion find = new TerminoExportacion();
        Query<TerminoExportacion> result = this.ds.find(TerminoExportacion.class).
                field("codigo").equal(terminoExportacion.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public TerminoExportacion findByCodigo(Integer terminoExportacion) {
        TerminoExportacion find = new TerminoExportacion();
        Query<TerminoExportacion> result = this.ds.find(TerminoExportacion.class).
                field("codigo").equal(terminoExportacion).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(TerminoExportacion terminoExportacion) {
        this.ds.delete(terminoExportacion);
    }

    public Boolean deteleFlag(TerminoExportacion terminoExportacion) {
        Query<TerminoExportacion> query = this.ds.createQuery(TerminoExportacion.class);
        terminoExportacion.setFlag(0);
        query.and(
                query.criteria("codigo").equal(terminoExportacion.getCodigo())
        );
        UpdateOperations<TerminoExportacion> update = this.ds.createUpdateOperations(TerminoExportacion.class);
        update.set("flag", terminoExportacion.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(TerminoExportacion terminoExportacion) {
        Query<TerminoExportacion> query = this.ds.createQuery(TerminoExportacion.class);
        query.and(
                query.criteria("codigo").equal(terminoExportacion.getCodigo())
        );
        UpdateOperations<TerminoExportacion> update = this.ds.createUpdateOperations(TerminoExportacion.class);
        update.set("descripcion", terminoExportacion.getDescripcion()).
                set("valor", terminoExportacion.getValor()).
                set("username", terminoExportacion.getUsername()).
                set("flag", terminoExportacion.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

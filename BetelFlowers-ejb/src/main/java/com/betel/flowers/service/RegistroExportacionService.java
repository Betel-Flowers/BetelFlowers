/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.RegistroExportacion;
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
public class RegistroExportacionService implements Serializable {

    private static final long serialVersionUID = -3815859191463165721L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(RegistroExportacion registroExportacion) {
        Boolean exito = Boolean.FALSE;
        RegistroExportacion axu = this.findByCodigo(registroExportacion);
        if (axu.getId() == null) {
            registroExportacion.setCodigo(this.obtenerCodigo());
            this.ds.save(registroExportacion);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<RegistroExportacion> especies = this.ds.find(RegistroExportacion.class).asList();
        if (especies == null) {
            especies = new ArrayList<>();
        }
        Integer size = especies.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<RegistroExportacion> obtenerLista() {
        List<RegistroExportacion> registroExportacions = this.ds.find(RegistroExportacion.class).asList();
        return registroExportacions;
    }

    public RegistroExportacion findByCodigo(RegistroExportacion registroExportacion) {
        RegistroExportacion find = new RegistroExportacion();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("codigo").equal(registroExportacion.getCodigo());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public RegistroExportacion findByCodigo(String registroExportacion) {
        RegistroExportacion find = new RegistroExportacion();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("codigo").equal(registroExportacion);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(RegistroExportacion registroExportacion) {
        this.ds.delete(registroExportacion);
    }

    public Boolean update(RegistroExportacion registroExportacion) {
        Query<RegistroExportacion> query = this.ds.createQuery(RegistroExportacion.class);
        query.and(
                query.criteria("codigo").equal(registroExportacion.getCodigo())
        );
        UpdateOperations<RegistroExportacion> update = this.ds.createUpdateOperations(RegistroExportacion.class);
        update.set("cantidad", registroExportacion.getNumeroRamos()).
                set("barcode", registroExportacion.getBarcode()).
                set("bodega", registroExportacion.getBodega()).
                set("variedad", registroExportacion.getVariedad()).
                set("username", registroExportacion.getUsername());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

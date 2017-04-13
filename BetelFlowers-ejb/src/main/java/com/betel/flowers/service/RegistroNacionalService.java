/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.RegistroNacional;
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
public class RegistroNacionalService implements Serializable {

    private static final long serialVersionUID = -1251854078186099674L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(RegistroNacional registroNacional) {
        Boolean exito = Boolean.FALSE;
        RegistroNacional axu = this.findByCodigo(registroNacional);
        if (axu.getId() == null) {
            registroNacional.setCodigo(this.obtenerCodigo());
            this.ds.save(registroNacional);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<RegistroNacional> especies = this.ds.find(RegistroNacional.class).asList();
        if (especies == null) {
            especies = new ArrayList<>();
        }
        Integer size = especies.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<RegistroNacional> obtenerLista() {
        List<RegistroNacional> registroNacionals = this.ds.find(RegistroNacional.class).asList();
        return registroNacionals;
    }

    public RegistroNacional findByCodigo(RegistroNacional registroNacional) {
        RegistroNacional find = new RegistroNacional();
        Query<RegistroNacional> result = this.ds.find(RegistroNacional.class).
                field("codigo").equal(registroNacional.getCodigo());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public RegistroNacional findByCodigo(String registroNacional) {
        RegistroNacional find = new RegistroNacional();
        Query<RegistroNacional> result = this.ds.find(RegistroNacional.class).
                field("codigo").equal(registroNacional);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(RegistroNacional registroNacional) {
        this.ds.delete(registroNacional);
    }

    public Boolean update(RegistroNacional registroNacional) {
        Query<RegistroNacional> query = this.ds.createQuery(RegistroNacional.class);
        query.and(
                query.criteria("codigo").equal(registroNacional.getCodigo())
        );
        UpdateOperations<RegistroNacional> update = this.ds.createUpdateOperations(RegistroNacional.class);
        update.set("numeroTallos", registroNacional.getTotalNumeroTallos()).
                set("fechaClasificacion", registroNacional.getFechaClasificacion()).
                set("bodega", registroNacional.getBodega()).
                set("detalle", registroNacional.getDetalle()).
                set("variedad", registroNacional.getVariedad()).
                set("username", registroNacional.getUsername());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

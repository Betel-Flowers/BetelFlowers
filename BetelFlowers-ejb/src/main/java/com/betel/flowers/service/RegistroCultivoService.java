/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.RegistroCultivo;
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
public class RegistroCultivoService implements Serializable {

    private static final long serialVersionUID = -2851043732113461605L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(RegistroCultivo registroCultivo) {
        Boolean exito = Boolean.FALSE;
        RegistroCultivo axu = this.findByCodigo(registroCultivo);
        if (axu.getId() == null) {
            registroCultivo.setCodigo(this.obtenerCodigo());
            this.ds.save(registroCultivo);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private String obtenerCodigo() {
        String Codigo = "BETEL-RC";
        List<RegistroCultivo> especies = this.ds.find(RegistroCultivo.class).asList();
        if (especies == null) {
            especies = new ArrayList<>();
        }
        Integer size = especies.size();
        Integer number = 1000 + 1 * size;
        return Codigo + number;
    }

    public List<RegistroCultivo> obtenerLista() {
        List<RegistroCultivo> registroCultivos = this.ds.find(RegistroCultivo.class).asList();
        return registroCultivos;
    }

    public RegistroCultivo findByCodigo(RegistroCultivo registroCultivo) {
        RegistroCultivo find = new RegistroCultivo();
        Query<RegistroCultivo> result = this.ds.find(RegistroCultivo.class).
                field("codigo").equal(registroCultivo.getCodigo());
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public RegistroCultivo findByCodigo(String registroCultivo) {
        RegistroCultivo find = new RegistroCultivo();
        Query<RegistroCultivo> result = this.ds.find(RegistroCultivo.class).
                field("codigo").equal(registroCultivo);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(RegistroCultivo registroCultivo) {
        this.ds.delete(registroCultivo);
    }

    public Boolean update(RegistroCultivo registroCultivo) {
        Query<RegistroCultivo> query = this.ds.createQuery(RegistroCultivo.class);
        query.and(
                query.criteria("codigo").equal(registroCultivo.getCodigo())
        );
        UpdateOperations<RegistroCultivo> update = this.ds.createUpdateOperations(RegistroCultivo.class);
        update.set("malla", registroCultivo.getMalla()).
                set("tina", registroCultivo.getTina()).
                set("numeroRecipientes", registroCultivo.getNumeroRecipientes()).
                set("numeroTallosRecipiente", registroCultivo.getNumeroTallosRecipiente()).
                set("bloque", registroCultivo.getBloque()).
                set("variedad", registroCultivo.getVariedad()).
                set("username", registroCultivo.getUsername());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Causa;
import com.betel.flowers.model.Motivo;
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
public class MotivoService implements Serializable {

    private static final long serialVersionUID = -3614662803767464984L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Motivo motivo) {
        Boolean exito = Boolean.FALSE;
        Motivo axu = this.findByCodigo(motivo);
        if (axu.getId() == null) {
            motivo.setCodigo(this.obtenerCodigo());
            motivo.setFlag(1);
            this.ds.save(motivo);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Motivo> motivos = this.ds.find(Motivo.class).asList();
        if (motivos == null) {
            motivos = new ArrayList<>();
        }
        Integer size = motivos.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Motivo> obtenerLista() {
        List<Motivo> motivos = this.ds.find(Motivo.class).asList();
        return motivos;
    }
    
    public List<Motivo> obtenerListCausaFlag(Causa causa,Integer flag) {
        List<Motivo> list = new ArrayList<>();
        Query<Motivo> result = this.ds.find(Motivo.class).
                field("flag").equal(flag).
                field("causa").equal(causa);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<Motivo> obtenerListFlag(Integer flag) {
        List<Motivo> list = new ArrayList<>();
        Query<Motivo> result = this.ds.find(Motivo.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Motivo findByCodigo(Motivo motivo) {
        Motivo find = new Motivo();
        Query<Motivo> result = this.ds.find(Motivo.class).
                field("codigo").equal(motivo.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Motivo findByCodigo(Integer motivo) {
        Motivo find = new Motivo();
        Query<Motivo> result = this.ds.find(Motivo.class).
                field("codigo").equal(motivo).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Motivo motivo) {
        this.ds.delete(motivo);
    }

    public Boolean deteleFlag(Motivo motivo) {
        Query<Motivo> query = this.ds.createQuery(Motivo.class);
        motivo.setFlag(0);
        query.and(
                query.criteria("codigo").equal(motivo.getCodigo())
        );
        UpdateOperations<Motivo> update = this.ds.createUpdateOperations(Motivo.class);
        update.set("flag", motivo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Motivo motivo) {
        Query<Motivo> query = this.ds.createQuery(Motivo.class);
        query.and(
                query.criteria("codigo").equal(motivo.getCodigo())
        );
        UpdateOperations<Motivo> update = this.ds.createUpdateOperations(Motivo.class);
        update.set("descripcion", motivo.getDescripcion()).
                set("cantidad", motivo.getCantidad()).
                set("causa", motivo.getCausa()).
                set("flag", motivo.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

}

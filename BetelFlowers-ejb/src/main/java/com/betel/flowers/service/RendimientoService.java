/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Rendimiento;
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
public class RendimientoService implements Serializable{
    
    private static final long serialVersionUID = 6042708922228743461L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Rendimiento rendimiento) {
        Boolean exito = Boolean.FALSE;
        Rendimiento axu = this.findByCodigo(rendimiento);
        if (axu.getId() == null) {
            rendimiento.setCodigo(this.obtenerCodigo());
            rendimiento.setFlag(1);
            this.ds.save(rendimiento);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Rendimiento> rendimientos = this.ds.find(Rendimiento.class).asList();
        if (rendimientos == null) {
            rendimientos = new ArrayList<>();
        }
        Integer size = rendimientos.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Rendimiento> obtenerLista() {
        List<Rendimiento> rendimientos = this.ds.find(Rendimiento.class).asList();
        return rendimientos;
    }

    public List<Rendimiento> obtenerListFlag(Integer flag) {
        List<Rendimiento> list = new ArrayList<>();
        Query<Rendimiento> result = this.ds.find(Rendimiento.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Rendimiento findByCodigo(Rendimiento rendimiento) {
        Rendimiento find = new Rendimiento();
        Query<Rendimiento> result = this.ds.find(Rendimiento.class).
                field("codigo").equal(rendimiento.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Rendimiento findByCodigo(Integer rendimiento) {
        Rendimiento find = new Rendimiento();
        Query<Rendimiento> result = this.ds.find(Rendimiento.class).
                field("codigo").equal(rendimiento).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Rendimiento rendimiento) {
        this.ds.delete(rendimiento);
    }

    public Boolean deteleFlag(Rendimiento rendimiento) {
        Query<Rendimiento> query = this.ds.createQuery(Rendimiento.class);
        rendimiento.setFlag(0);
        query.and(
                query.criteria("codigo").equal(rendimiento.getCodigo())
        );
        UpdateOperations<Rendimiento> update = this.ds.createUpdateOperations(Rendimiento.class);
        update.set("flag", rendimiento.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(Rendimiento rendimiento) {
        Query<Rendimiento> query = this.ds.createQuery(Rendimiento.class);
        query.and(
                query.criteria("codigo").equal(rendimiento.getCodigo())
        );
        UpdateOperations<Rendimiento> update = this.ds.createUpdateOperations(Rendimiento.class);
        update.set("barcode", rendimiento.getBarcode()).
                set("tipoTrabajo", rendimiento.getTipoTrabajo()).
                set("fechaIn", rendimiento.getFechaIn()).
                set("fechaFin", rendimiento.getFechaFin()).
                set("cantidad", rendimiento.getCantidad()).
                set("operario", rendimiento.getOperario()).
                set("username", rendimiento.getUsername()).
                set("flag", rendimiento.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

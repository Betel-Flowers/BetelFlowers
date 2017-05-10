/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Causa;
import com.betel.flowers.model.MotivoEmpaque;
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
public class MotivoEmpaqueService implements Serializable{
    
    private static final long serialVersionUID = -8485204392935074313L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(MotivoEmpaque motivoEmpaque) {
        Boolean exito = Boolean.FALSE;
        MotivoEmpaque axu = this.findByCodigo(motivoEmpaque);
        if (axu.getId() == null) {
            motivoEmpaque.setCodigo(this.obtenerCodigo());
            motivoEmpaque.setFlag(1);
            this.ds.save(motivoEmpaque);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<MotivoEmpaque> motivoEmpaques = this.ds.find(MotivoEmpaque.class).asList();
        if (motivoEmpaques == null) {
            motivoEmpaques = new ArrayList<>();
        }
        Integer size = motivoEmpaques.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<MotivoEmpaque> obtenerLista() {
        List<MotivoEmpaque> motivoEmpaques = this.ds.find(MotivoEmpaque.class).asList();
        return motivoEmpaques;
    }

    public List<MotivoEmpaque> obtenerListCausaFlag(Causa causa, Integer flag) {
        List<MotivoEmpaque> list = new ArrayList<>();
        Query<MotivoEmpaque> result = this.ds.find(MotivoEmpaque.class).
                field("flag").equal(flag).
                field("causa").equal(causa);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<MotivoEmpaque> obtenerListFlag(Integer flag) {
        List<MotivoEmpaque> list = new ArrayList<>();
        Query<MotivoEmpaque> result = this.ds.find(MotivoEmpaque.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public MotivoEmpaque findByCodigo(MotivoEmpaque motivoEmpaque) {
        MotivoEmpaque find = new MotivoEmpaque();
        Query<MotivoEmpaque> result = this.ds.find(MotivoEmpaque.class).
                field("codigo").equal(motivoEmpaque.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public MotivoEmpaque findByDescripcion(MotivoEmpaque motivoEmpaque) {
        MotivoEmpaque find = new MotivoEmpaque();
        Query<MotivoEmpaque> result = this.ds.find(MotivoEmpaque.class).
                field("descripcion").equal(motivoEmpaque.getDescripcion()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public MotivoEmpaque findByCodigo(Integer motivoEmpaque) {
        MotivoEmpaque find = new MotivoEmpaque();
        Query<MotivoEmpaque> result = this.ds.find(MotivoEmpaque.class).
                field("codigo").equal(motivoEmpaque).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(MotivoEmpaque motivoEmpaque) {
        this.ds.delete(motivoEmpaque);
    }

    public Boolean deteleFlag(MotivoEmpaque motivoEmpaque) {
        Query<MotivoEmpaque> query = this.ds.createQuery(MotivoEmpaque.class);
        motivoEmpaque.setFlag(0);
        query.and(
                query.criteria("codigo").equal(motivoEmpaque.getCodigo())
        );
        UpdateOperations<MotivoEmpaque> update = this.ds.createUpdateOperations(MotivoEmpaque.class);
        update.set("flag", motivoEmpaque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(MotivoEmpaque motivoEmpaque) {
        Query<MotivoEmpaque> query = this.ds.createQuery(MotivoEmpaque.class);
        query.and(
                query.criteria("codigo").equal(motivoEmpaque.getCodigo())
        );
        UpdateOperations<MotivoEmpaque> update = this.ds.createUpdateOperations(MotivoEmpaque.class);
        update.set("descripcion", motivoEmpaque.getDescripcion()).
                set("causa", motivoEmpaque.getCausa()).
                set("username", motivoEmpaque.getUsername()).
                set("flag", motivoEmpaque.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

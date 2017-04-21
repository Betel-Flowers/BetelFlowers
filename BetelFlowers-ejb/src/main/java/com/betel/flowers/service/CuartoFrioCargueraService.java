/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.CuartoFrioCarguera;
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
public class CuartoFrioCargueraService implements Serializable{
    
    private static final long serialVersionUID = 8572598469241032203L;
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(CuartoFrioCarguera cuartoFrioCarguera) {
        Boolean exito = Boolean.FALSE;
        CuartoFrioCarguera axu = this.findByCodigo(cuartoFrioCarguera);
        if (axu.getId() == null) {
            cuartoFrioCarguera.setCodigo(this.obtenerCodigo());
            cuartoFrioCarguera.setFlag(1);
            this.ds.save(cuartoFrioCarguera);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<CuartoFrioCarguera> cuartoFrioCargueras = this.ds.find(CuartoFrioCarguera.class).asList();
        if (cuartoFrioCargueras == null) {
            cuartoFrioCargueras = new ArrayList<>();
        }
        Integer size = cuartoFrioCargueras.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<CuartoFrioCarguera> obtenerLista() {
        List<CuartoFrioCarguera> cuartoFrioCargueras = this.ds.find(CuartoFrioCarguera.class).asList();
        return cuartoFrioCargueras;
    }

    public List<CuartoFrioCarguera> obtenerListFlag(Integer flag) {
        List<CuartoFrioCarguera> list = new ArrayList<>();
        Query<CuartoFrioCarguera> result = this.ds.find(CuartoFrioCarguera.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public CuartoFrioCarguera findByCodigo(CuartoFrioCarguera cuartoFrioCarguera) {
        CuartoFrioCarguera find = new CuartoFrioCarguera();
        Query<CuartoFrioCarguera> result = this.ds.find(CuartoFrioCarguera.class).
                field("codigo").equal(cuartoFrioCarguera.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public CuartoFrioCarguera findByCodigo(Integer cuartoFrioCarguera) {
        CuartoFrioCarguera find = new CuartoFrioCarguera();
        Query<CuartoFrioCarguera> result = this.ds.find(CuartoFrioCarguera.class).
                field("codigo").equal(cuartoFrioCarguera).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    

    public void delete(CuartoFrioCarguera cuartoFrioCarguera) {
        this.ds.delete(cuartoFrioCarguera);
    }

    public Boolean deteleFlag(CuartoFrioCarguera cuartoFrioCarguera) {
        Query<CuartoFrioCarguera> query = this.ds.createQuery(CuartoFrioCarguera.class);
        cuartoFrioCarguera.setFlag(0);
        query.and(
                query.criteria("codigo").equal(cuartoFrioCarguera.getCodigo())
        );
        UpdateOperations<CuartoFrioCarguera> update = this.ds.createUpdateOperations(CuartoFrioCarguera.class);
        update.set("flag", cuartoFrioCarguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(CuartoFrioCarguera cuartoFrioCarguera) {
        Query<CuartoFrioCarguera> query = this.ds.createQuery(CuartoFrioCarguera.class);
        query.and(
                query.criteria("codigo").equal(cuartoFrioCarguera.getCodigo())
        );
        UpdateOperations<CuartoFrioCarguera> update = this.ds.createUpdateOperations(CuartoFrioCarguera.class);
        update.set("descripcion", cuartoFrioCarguera.getDescripcion()).
                set("bodega", cuartoFrioCarguera.getBodega()).
                set("username", cuartoFrioCarguera.getUsername()).
                set("flag", cuartoFrioCarguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

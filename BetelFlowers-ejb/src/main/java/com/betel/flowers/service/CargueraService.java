/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Carguera;
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
public class CargueraService implements Serializable {

    private static final long serialVersionUID = -739231408017324420L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Carguera carguera) {
        Boolean exito = Boolean.FALSE;
        Carguera axu = this.findByCodigo(carguera);
        if (axu.getId() == null) {
            carguera.setCodigo(this.obtenerCodigo());
            carguera.setFlag(1);
            this.ds.save(carguera);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Carguera> cargueras = this.ds.find(Carguera.class).asList();
        if (cargueras == null) {
            cargueras = new ArrayList<>();
        }
        Integer size = cargueras.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<Carguera> obtenerLista() {
        List<Carguera> cargueras = this.ds.find(Carguera.class).asList();
        return cargueras;
    }

    public List<Carguera> obtenerListFlag(Integer flag) {
        List<Carguera> list = new ArrayList<>();
        Query<Carguera> result = this.ds.find(Carguera.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Carguera findByCodigo(Carguera carguera) {
        Carguera find = new Carguera();
        Query<Carguera> result = this.ds.find(Carguera.class).
                field("codigo").equal(carguera.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Carguera findByCodigo(Integer carguera) {
        Carguera find = new Carguera();
        Query<Carguera> result = this.ds.find(Carguera.class).
                field("codigo").equal(carguera).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Carguera carguera) {
        this.ds.delete(carguera);
    }

    public Boolean deteleFlag(Carguera carguera) {
        Query<Carguera> query = this.ds.createQuery(Carguera.class);
        carguera.setFlag(0);
        query.and(
                query.criteria("codigo").equal(carguera.getCodigo())
        );
        UpdateOperations<Carguera> update = this.ds.createUpdateOperations(Carguera.class);
        update.set("flag", carguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Carguera carguera) {
        Query<Carguera> query = this.ds.createQuery(Carguera.class);
        query.and(
                query.criteria("codigo").equal(carguera.getCodigo())
        );
        UpdateOperations<Carguera> update = this.ds.createUpdateOperations(Carguera.class);
        update.set("nombre", carguera.getNombre()).
                set("ruc", carguera.getRuc()).
                set("telefono", carguera.getTelefono()).
                set("correo1", carguera.getCorreo1()).
                set("correo2", carguera.getCorreo2()).
                set("flag", carguera.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Bloque;
import com.betel.flowers.model.Variedad;
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
public class VariedadService implements Serializable {

    private static final long serialVersionUID = -66997027216307602L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Variedad variedad) {
        Boolean exito = Boolean.FALSE;
        Variedad axu = this.findByCodigo(variedad);
        if (axu.getId() == null) {
            variedad.setCodigo(this.obtenerCodigo());
            variedad.setFlag(1);
            this.ds.save(variedad);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public Boolean add(Variedad variedad) {
        Boolean exito = Boolean.FALSE;
        if (variedad.getRamos().isEmpty()
                && variedad.getLongitudes().isEmpty()
                && variedad.getPuntosCorte().isEmpty()) {
            this.ds.save(variedad);
            exito = Boolean.TRUE;
        } else {
            updateList(variedad);
        }
        return exito;
    }

    private String obtenerCodigo() {
        String Codigo = "BETEL-V";
        List<Variedad> variedads = this.ds.find(Variedad.class).asList();
        if (variedads == null) {
            variedads = new ArrayList<>();
        }
        Integer size = variedads.size();
        Integer number = 1000 + 1 * size;
        return Codigo + number;
    }

    public List<Variedad> obtenerListFlag(Integer flag) {
        List<Variedad> list = new ArrayList<>();
        Query<Variedad> result = this.ds.find(Variedad.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<Variedad> obtenerListaBloque(Bloque bloque) {
        List<Variedad> list = new ArrayList<>();
        Query<Variedad> result = this.ds.find(Variedad.class).
                field("bloque").equal(bloque).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<Variedad> obtenerLista() {
        List<Variedad> variedads = this.ds.find(Variedad.class).asList();
        return variedads;
    }

    public Variedad findByCodigo(Variedad variedad) {
        Variedad find = new Variedad();
        Query<Variedad> result = this.ds.find(Variedad.class).
                field("codigo").equal(variedad.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Variedad findByCodigo(String variedad) {
        Variedad find = new Variedad();
        Query<Variedad> result = this.ds.find(Variedad.class).
                field("codigo").equal(variedad).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Variedad variedad) {
        this.ds.delete(variedad);
    }

    public Boolean deteleFlag(Variedad variedad) {
        Query<Variedad> query = this.ds.createQuery(Variedad.class);
        variedad.setFlag(0);
        query.and(
                query.criteria("codigo").equal(variedad.getCodigo())
        );
        UpdateOperations<Variedad> update = this.ds.createUpdateOperations(Variedad.class);
        update.set("flag", variedad.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean updateList(Variedad variedad) {
        Query<Variedad> query = this.ds.createQuery(Variedad.class);
        query.and(
                query.criteria("codigo").equal(variedad.getCodigo())
        );
        UpdateOperations<Variedad> update = this.ds.createUpdateOperations(Variedad.class);
        update.set("ramos", variedad.getRamos()).
                set("longitudes", variedad.getLongitudes()).
                set("puntosCorte", variedad.getPuntosCorte());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Variedad variedad) {
        Query<Variedad> query = this.ds.createQuery(Variedad.class);
        query.and(
                query.criteria("codigo").equal(variedad.getCodigo())
        );
        UpdateOperations<Variedad> update = this.ds.createUpdateOperations(Variedad.class);
        update.set("especie", variedad.getEspecie()).
                set("nombre", variedad.getNombre()).
                set("color", variedad.getColor()).
                set("urlFoto", variedad.getUrlFoto()).
                set("codigoFoto", variedad.getCodigoFoto()).
                set("tiempoVida", variedad.getTiempoVida()).
                set("ramos", variedad.getRamos()).
                set("longitudes", variedad.getLongitudes()).
                set("puntosCorte", variedad.getPuntosCorte()).
                set("username", variedad.getUsername()).
                set("flag", variedad.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

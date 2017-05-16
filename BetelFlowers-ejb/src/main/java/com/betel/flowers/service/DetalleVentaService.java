/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.DetalleVenta;
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
public class DetalleVentaService implements Serializable {

    private static final long serialVersionUID = -8503226659531786369L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(DetalleVenta detalleVenta) {
        Boolean exito = Boolean.FALSE;
        DetalleVenta axu = this.findByCodigo(detalleVenta);
        if (axu.getId() == null) {
            detalleVenta.setCodigo(this.obtenerCodigo());
            detalleVenta.setFlag(1);
            this.ds.save(detalleVenta);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<DetalleVenta> detalleVentas = this.ds.find(DetalleVenta.class).asList();
        if (detalleVentas == null) {
            detalleVentas = new ArrayList<>();
        }
        Integer size = detalleVentas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<DetalleVenta> obtenerLista() {
        List<DetalleVenta> detalleVentas = this.ds.find(DetalleVenta.class).asList();
        return detalleVentas;
    }

    public List<DetalleVenta> obtenerListFlag(Integer flag) {
        List<DetalleVenta> list = new ArrayList<>();
        Query<DetalleVenta> result = this.ds.find(DetalleVenta.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public DetalleVenta findByCodigo(DetalleVenta detalleVenta) {
        DetalleVenta find = new DetalleVenta();
        Query<DetalleVenta> result = this.ds.find(DetalleVenta.class).
                field("codigo").equal(detalleVenta.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public DetalleVenta findByCodigo(Integer detalleVenta) {
        DetalleVenta find = new DetalleVenta();
        Query<DetalleVenta> result = this.ds.find(DetalleVenta.class).
                field("codigo").equal(detalleVenta).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(DetalleVenta detalleVenta) {
        this.ds.delete(detalleVenta);
    }

    public Boolean deteleFlag(DetalleVenta detalleVenta) {
        Query<DetalleVenta> query = this.ds.createQuery(DetalleVenta.class);
        detalleVenta.setFlag(0);
        query.and(
                query.criteria("codigo").equal(detalleVenta.getCodigo())
        );
        UpdateOperations<DetalleVenta> update = this.ds.createUpdateOperations(DetalleVenta.class);
        update.set("flag", detalleVenta.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(DetalleVenta detalleVenta) {
        Query<DetalleVenta> query = this.ds.createQuery(DetalleVenta.class);
        query.and(
                query.criteria("codigo").equal(detalleVenta.getCodigo())
        );
        UpdateOperations<DetalleVenta> update = this.ds.createUpdateOperations(DetalleVenta.class);
        update.set("cantidadCajas", detalleVenta.getCantidadCajas()).
                set("totalTallos", detalleVenta.getTotalTallos()).
                set("barcode", detalleVenta.getBarcode()).
                set("totalTallosCaja", detalleVenta.getTotalTallosCaja()).
                set("subTotalCaja", detalleVenta.getSubTotalCaja()).
                set("cajaTipo", detalleVenta.getCajaTipo()).
                set("marcaCaja", detalleVenta.getMarcaCaja()).
                set("detalleCajaVenta", detalleVenta.getDetalleCajaVenta()).
                set("username", detalleVenta.getUsername()).
                set("flag", detalleVenta.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

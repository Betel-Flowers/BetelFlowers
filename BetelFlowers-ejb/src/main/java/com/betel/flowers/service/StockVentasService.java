/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.StockVenta;
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
public class StockVentasService implements Serializable {

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(StockVenta stockVenta) {
        Boolean exito = Boolean.FALSE;
        StockVenta axu = this.findByCodigo(stockVenta);
        if (axu.getId() == null) {
            stockVenta.setCodigo(this.obtenerCodigo());
            stockVenta.setFlag(1);
            this.ds.save(stockVenta);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<StockVenta> stockVentas = this.ds.find(StockVenta.class).asList();
        if (stockVentas == null) {
            stockVentas = new ArrayList<>();
        }
        Integer size = stockVentas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<StockVenta> obtenerLista() {
        List<StockVenta> stockVentas = this.ds.find(StockVenta.class).asList();
        return stockVentas;
    }

    public List<StockVenta> obtenerListFlag(Integer flag) {
        List<StockVenta> list = new ArrayList<>();
        Query<StockVenta> result = this.ds.find(StockVenta.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public StockVenta findByCodigo(StockVenta stockVenta) {
        StockVenta find = new StockVenta();
        Query<StockVenta> result = this.ds.find(StockVenta.class).
                field("codigo").equal(stockVenta.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public StockVenta findByCodigo(Integer stockVenta) {
        StockVenta find = new StockVenta();
        Query<StockVenta> result = this.ds.find(StockVenta.class).
                field("codigo").equal(stockVenta).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(StockVenta stockVenta) {
        this.ds.delete(stockVenta);
    }

    public Boolean deteleFlag(StockVenta stockVenta) {
        Query<StockVenta> query = this.ds.createQuery(StockVenta.class);
        stockVenta.setFlag(0);
        query.and(
                query.criteria("codigo").equal(stockVenta.getCodigo())
        );
        UpdateOperations<StockVenta> update = this.ds.createUpdateOperations(StockVenta.class);
        update.set("flag", stockVenta.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(StockVenta stockVenta) {
        Query<StockVenta> query = this.ds.createQuery(StockVenta.class);
        query.and(
                query.criteria("codigo").equal(stockVenta.getCodigo())
        );
        UpdateOperations<StockVenta> update = this.ds.createUpdateOperations(StockVenta.class);
        update.set("numeroCajas", stockVenta.getNumeroCajas()).
                set("numeroRamos", stockVenta.getNumeroRamos()).
                set("numeroTallosRamo", stockVenta.getNumeroTallosRamo()).
                set("longitud", stockVenta.getLongitud()).
                set("puntoCorte", stockVenta.getPuntoCorte()).
                set("totalTallos", stockVenta.getTotalTallos()).
                set("precio", stockVenta.getPrecio()).
                set("subtotal", stockVenta.getSubtotal()).
                set("barcode", stockVenta.getBarcode()).
                set("variedad", stockVenta.getVariedad()).
                set("caja", stockVenta.getCaja()).
                set("username", stockVenta.getUsername()).
                set("flag", stockVenta.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Coordinacion;
import com.betel.flowers.model.RegistroVenta;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.StringUtils;
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
public class RegistroVentaService implements Serializable {
    
    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();
    
    public Boolean insert(RegistroVenta venta) {
        Boolean exito = Boolean.FALSE;
        RegistroVenta axu = this.findByCodigo(venta);
        if (axu.getId() == null) {
            venta.setCodigo(this.obtenerCodigo());
            venta.setNumberPaking(this.obtenerCodigo() * 10);
            venta.setBarcode(obtenerBarcode());
            venta.setFlag(1);
            this.ds.save(venta);
            exito = Boolean.TRUE;
        }
        return exito;
    }
    
    private Integer obtenerCodigo() {
        List<RegistroVenta> ventas = this.ds.find(RegistroVenta.class).asList();
        if (ventas == null) {
            ventas = new ArrayList<>();
        }
        Integer size = ventas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }
    
    private String obtenerBarcode() {
        List<RegistroVenta> ventas = this.ds.find(RegistroVenta.class).asList();
        if (ventas == null) {
            ventas = new ArrayList<>();
        }
        Integer size = ventas.size();
        Integer number = 1000 + 1 * size;
        return "BETEL-VENTY-COOR-"+number;
    }
    
    public List<RegistroVenta> obtenerLista() {
        List<RegistroVenta> ventas = this.ds.find(RegistroVenta.class).asList();
        return ventas;
    }
    
    public List<RegistroVenta> obtenerListFlag(Integer flag) {
        List<RegistroVenta> list = new ArrayList<>();
        Query<RegistroVenta> result = this.ds.find(RegistroVenta.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setData(new Coordinacion());
            }
        }
        return list;
    }
    
    public RegistroVenta findByCodigo(RegistroVenta venta) {
        RegistroVenta find = new RegistroVenta();
        Query<RegistroVenta> result = this.ds.find(RegistroVenta.class).
                field("codigo").equal(venta.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public RegistroVenta findByCodigo(Integer venta) {
        RegistroVenta find = new RegistroVenta();
        Query<RegistroVenta> result = this.ds.find(RegistroVenta.class).
                field("codigo").equal(venta).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public void delete(RegistroVenta venta) {
        this.ds.delete(venta);
    }
    
    public Boolean deteleFlag(RegistroVenta venta) {
        Query<RegistroVenta> query = this.ds.createQuery(RegistroVenta.class);
        venta.setFlag(0);
        query.and(
                query.criteria("codigo").equal(venta.getCodigo())
        );
        UpdateOperations<RegistroVenta> update = this.ds.createUpdateOperations(RegistroVenta.class);
        update.set("flag", venta.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
    public Boolean update(RegistroVenta venta) {
        Query<RegistroVenta> query = this.ds.createQuery(RegistroVenta.class);
        query.and(
                query.criteria("codigo").equal(venta.getCodigo())
        );
        UpdateOperations<RegistroVenta> update = this.ds.createUpdateOperations(RegistroVenta.class);
        update.set("numberPaking", venta.getNumberPaking()).
                set("numberSRI", venta.getSecuencialSRI()).
                set("fechaSRI", venta.getFechaSRI()).
                set("AWB", venta.getAWB()).
                set("HAWB", venta.getHAWB()).
                set("observacion", venta.getObservacion()).
                set("barcode", venta.getBarcode()).
                set("username", venta.getUsername()).
                set("cliente", venta.getCliente()).
                set("data", venta.getData()).
                set("matrixVenta", venta.getMatrixVenta());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

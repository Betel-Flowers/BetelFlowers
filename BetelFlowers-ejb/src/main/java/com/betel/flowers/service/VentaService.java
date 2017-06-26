/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.ItemVariedadVenta;
import com.betel.flowers.model.RegistroExportacion;
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
public class VentaService implements Serializable {

    private static final long serialVersionUID = -3815859191463165721L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(RegistroExportacion registroExportacion) {
        Boolean exito = Boolean.FALSE;
        RegistroExportacion axu = this.findByCodigo(registroExportacion);
        if (axu.getId() == null) {
            registroExportacion.setCodigo(this.obtenerCodigo());
            registroExportacion.setFlag(1);
            this.ds.save(registroExportacion);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<RegistroExportacion> especies = this.ds.find(RegistroExportacion.class).asList();
        if (especies == null) {
            especies = new ArrayList<>();
        }
        Integer size = especies.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<RegistroExportacion> obtenerLista() {
        List<RegistroExportacion> registroExportacions = this.ds.find(RegistroExportacion.class).asList();
        return registroExportacions;
    }

    public RegistroExportacion findByCodigo(RegistroExportacion registroExportacion) {
        RegistroExportacion find = new RegistroExportacion();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("codigo").equal(registroExportacion.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public List<RegistroExportacion> obtenerListFlag(Integer flag) {
        List<RegistroExportacion> list = new ArrayList<>();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<RegistroExportacion> obtenerListBarcode(String barcode) {
        List<RegistroExportacion> list = new ArrayList<>();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("barcode").equal(barcode).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<ItemVariedadVenta> obtenerListDisponibilidadFlag(RegistroExportacion find, Integer flag) {
        List<RegistroExportacion> list = new ArrayList<>();
        Query<RegistroExportacion> result = null;
        if (find.getVariedad().getGirasol()) {
            result = this.ds.find(RegistroExportacion.class).
                    field("bodega").equal(find.getBodega()).
                    field("variedad.especie").equal(find.getVariedad().getEspecie()).
                    field("flag").equal(flag).
                    order("creationDate");
        } else {
            result = this.ds.find(RegistroExportacion.class).
                    field("bodega").equal(find.getBodega()).
                    field("variedad.especie").equal(find.getVariedad().getEspecie()).
                    field("flag").equal(flag).
                    order("creationDate");
        }
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return detailListVentaStock(list);
    }

    private List<ItemVariedadVenta> detailListVentaStock(List<RegistroExportacion> registros) {
        List<ItemVariedadVenta> list = new ArrayList<>();
        for (RegistroExportacion regExpo : registros) {
            ItemVariedadVenta item = new ItemVariedadVenta();
            item.setRegistro(regExpo);
            list.add(item);
        }
        return list;
    }

    public RegistroExportacion findByCodigo(String registroExportacion) {
        RegistroExportacion find = new RegistroExportacion();
        Query<RegistroExportacion> result = this.ds.find(RegistroExportacion.class).
                field("codigo").equal(registroExportacion).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Boolean deteleFlag(RegistroExportacion usuario) {
        Query<RegistroExportacion> query = this.ds.createQuery(RegistroExportacion.class);
        usuario.setFlag(0);
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<RegistroExportacion> update = this.ds.createUpdateOperations(RegistroExportacion.class);
        update.set("flag", usuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public void delete(RegistroExportacion registroExportacion) {
        this.ds.delete(registroExportacion);
    }

    public Boolean update(RegistroExportacion registroExportacion) {
        Boolean exito = Boolean.FALSE;
        if (registroExportacion != null) {
            if (registroExportacion.getVariedad().getGirasol()) {
                exito = this.updateGlongitud(registroExportacion);
            } else {
                exito = this.updateLongitud(registroExportacion);
            }
        }
        return exito;
    }

    private Boolean updateLongitud(RegistroExportacion registroExportacion) {
        Query<RegistroExportacion> query = this.ds.createQuery(RegistroExportacion.class);
        query.and(
                query.criteria("codigo").equal(registroExportacion.getCodigo())
        );
        UpdateOperations<RegistroExportacion> update = this.ds.createUpdateOperations(RegistroExportacion.class);
        update.set("numeroRamos", registroExportacion.getNumeroRamos()).
                set("numeroTallosRamo", registroExportacion.getNumeroTallosRamo()).
                set("longitud", registroExportacion.getLongitud()).
                set("puntoCorte", registroExportacion.getPuntoCorte()).
                set("stock", registroExportacion.getStock()).
                set("totalTallos", registroExportacion.getTotalTallos()).
                set("barcode", registroExportacion.getBarcode()).
                set("xml", registroExportacion.getXml()).
                set("html", registroExportacion.getHtml()).
                set("pdf", registroExportacion.getPdf()).
                set("urlPdf", registroExportacion.getUrlPdf()).
                set("bodega", registroExportacion.getBodega()).
                set("variedad", registroExportacion.getVariedad()).
                set("rendimientos", registroExportacion.getRendimientos()).
                set("username", registroExportacion.getUsername()).
                set("flag", registroExportacion.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    private Boolean updateGlongitud(RegistroExportacion registroExportacion) {
        Query<RegistroExportacion> query = this.ds.createQuery(RegistroExportacion.class);
        query.and(
                query.criteria("codigo").equal(registroExportacion.getCodigo())
        );
        UpdateOperations<RegistroExportacion> update = this.ds.createUpdateOperations(RegistroExportacion.class);
        update.set("numeroRamos", registroExportacion.getNumeroRamos()).
                set("numeroTallosRamo", registroExportacion.getNumeroTallosRamo()).
                set("glongitud", registroExportacion.getGlongitud()).
                set("puntoCorte", registroExportacion.getPuntoCorte()).
                set("stock", registroExportacion.getStock()).
                set("totalTallos", registroExportacion.getTotalTallos()).
                set("barcode", registroExportacion.getBarcode()).
                set("xml", registroExportacion.getXml()).
                set("html", registroExportacion.getHtml()).
                set("pdf", registroExportacion.getPdf()).
                set("urlPdf", registroExportacion.getUrlPdf()).
                set("bodega", registroExportacion.getBodega()).
                set("variedad", registroExportacion.getVariedad()).
                set("rendimientos", registroExportacion.getRendimientos()).
                set("username", registroExportacion.getUsername()).
                set("flag", registroExportacion.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

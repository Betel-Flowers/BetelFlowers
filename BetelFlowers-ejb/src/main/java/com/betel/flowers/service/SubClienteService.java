/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.SubCliente;
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
public class SubClienteService implements Serializable {

    private static final long serialVersionUID = 6592951767436252262L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(SubCliente subCliente) {
        Boolean exito = Boolean.FALSE;
        SubCliente axu = findByCodigo(subCliente);
        if (axu.getId() == null) {
            subCliente.setCodigo(obtenerCodigo());
            subCliente.setFlag(1);
            this.ds.save(subCliente);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private String obtenerCodigo() {
        String Codigo = "BETEL-SC";
        List<SubCliente> subClientes = this.ds.find(SubCliente.class).asList();
        if (subClientes == null) {
            subClientes = new ArrayList<>();
        }
        Integer size = subClientes.size();
        Integer number = 1000 + 1 * size;
        return Codigo + number;
    }

    public List<SubCliente> obtenerListFlag(Integer flag) {
        List<SubCliente> list = new ArrayList<>();
        Query<SubCliente> result = this.ds.find(SubCliente.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<SubCliente> obtenerLista() {
        List<SubCliente> subClientes = this.ds.find(SubCliente.class).asList();
        return subClientes;
    }

    public SubCliente findByCodigo(SubCliente subCliente) {
        SubCliente find = new SubCliente();
        Query<SubCliente> result = this.ds.find(SubCliente.class).
                field("codigo").equal(subCliente.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public SubCliente findByCodigo(String subCliente) {
        SubCliente find = new SubCliente();
        Query<SubCliente> result = this.ds.find(SubCliente.class).
                field("codigo").equal(subCliente).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(SubCliente subCliente) {
        this.ds.delete(subCliente);
    }

    public Boolean deteleFlag(SubCliente subCliente) {
        Query<SubCliente> query = this.ds.createQuery(SubCliente.class);
        subCliente.setFlag(0);
        query.and(
                query.criteria("codigo").equal(subCliente.getCodigo())
        );
        UpdateOperations<SubCliente> update = this.ds.createUpdateOperations(SubCliente.class);
        update.set("flag", subCliente.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(SubCliente subCliente) {
        Query<SubCliente> query = this.ds.createQuery(SubCliente.class);
        query.and(
                query.criteria("codigo").equal(subCliente.getCodigo())
        );
        UpdateOperations<SubCliente> update = this.ds.createUpdateOperations(SubCliente.class);
        update.set("nombreContacto", subCliente.getNombreContacto()).
                set("empresa", subCliente.getEmpresa()).
                set("direccion", subCliente.getDireccion()).
                set("username", subCliente.getUsername()).
                set("flag", subCliente.getFlag()).
                set("ciudad", subCliente.getCiudad()).
                set("zona", subCliente.getZona()).
                set("telefonos", subCliente.getTelefonos()).
                set("correos", subCliente.getCorreos());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

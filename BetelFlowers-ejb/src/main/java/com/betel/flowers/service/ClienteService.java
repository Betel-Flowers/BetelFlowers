/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Cliente;
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
public class ClienteService implements Serializable {

    private static final long serialVersionUID = -5026284582912046911L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Cliente cliente) {
        Boolean exito = Boolean.FALSE;
        Cliente axu = findByCedula(cliente);
        if (axu.getId() == null) {
            cliente.setCodigo(obtenerCodigo());
            cliente.setFlag(1);
            cliente.setRuc(cliente.getRuc().trim());
            this.ds.save(cliente);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public Boolean addSubCliente(Cliente cliente) {
        Boolean exito = Boolean.FALSE;
        Cliente mcliente = findByCedula(cliente);
        if (mcliente.getSubClientes() != null
                && mcliente.getSubClientes().isEmpty()) {
            this.ds.save(cliente);
            exito = Boolean.TRUE;
        } else {
            updateList(cliente);
        }
        return exito;
    }

    private String obtenerCodigo() {
        String Codigo = "BETEL-C";
        List<Cliente> clientes = this.ds.find(Cliente.class).asList();
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
        Integer size = clientes.size();
        Integer number = 1000 + 1 * size;
        return Codigo + number;
    }

    public List<Cliente> obtenerListFlag(Integer flag) {
        List<Cliente> list = new ArrayList<>();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<Cliente> obtenerLista() {
        List<Cliente> clientes = this.ds.find(Cliente.class).asList();
        return clientes;
    }

    public Cliente findByCedula(Cliente cliente) {
        Cliente find = new Cliente();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("ruc").equal(cliente.getRuc()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Cliente findByCodigo(Cliente cliente) {
        Cliente find = new Cliente();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("codigo").equal(cliente.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Cliente findByCodigo(String cliente) {
        Cliente find = new Cliente();
        Query<Cliente> result = this.ds.find(Cliente.class).
                field("codigo").equal(cliente).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Cliente cliente) {
        this.ds.delete(cliente);
    }

    public Boolean deteleFlag(Cliente cliente) {
        Query<Cliente> query = this.ds.createQuery(Cliente.class);
        cliente.setFlag(0);
        query.and(
                query.criteria("codigo").equal(cliente.getCodigo())
        );
        UpdateOperations<Cliente> update = this.ds.createUpdateOperations(Cliente.class);
        update.set("flag", cliente.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean updateList(Cliente cliente) {
        Query<Cliente> query = this.ds.createQuery(Cliente.class);
        query.and(
                query.criteria("codigo").equal(cliente.getCodigo())
        );
        UpdateOperations<Cliente> update = this.ds.createUpdateOperations(Cliente.class);
        update.set("subClientes", cliente.getSubClientes());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Cliente cliente) {
        Query<Cliente> query = this.ds.createQuery(Cliente.class);
        query.and(
                query.criteria("codigo").equal(cliente.getCodigo())
        );
        UpdateOperations<Cliente> update = this.ds.createUpdateOperations(Cliente.class);
        update.set("comercializadora", cliente.getComercializadora()).
                set("exterior", cliente.getExterior()).
                set("local", cliente.getLocal()).
                set("nombreContacto", cliente.getNombreContacto()).
                set("nombres", cliente.getNombres()).
                set("apellidos", cliente.getApellidos()).
                set("ruc", cliente.getRuc()).
                set("direccion", cliente.getDireccion()).
                set("diasPago", cliente.getDiasPago()).
                set("limiteCredito", cliente.getLimiteCredito()).
                set("diasCredito", cliente.getDiasCredito()).
                set("impuestos", cliente.getImpuestos()).
                set("username", cliente.getUsername()).
                set("flag", cliente.getFlag()).
                set("ciudad", cliente.getCiudad()).
                set("telefonos", cliente.getTelefonos()).
                set("correos", cliente.getCorreos()).
                set("cajas", cliente.getCajas()).
                set("subClientes", cliente.getSubClientes());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
    
}

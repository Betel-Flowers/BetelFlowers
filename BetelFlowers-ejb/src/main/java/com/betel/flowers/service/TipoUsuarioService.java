/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.model.TipoUsuario;
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
public class TipoUsuarioService implements Serializable {

    private static final long serialVersionUID = 4847151764956372364L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(TipoUsuario tipoUsuario) {
        Boolean exito = Boolean.FALSE;
        TipoUsuario axu = this.findByCodigo(tipoUsuario);
        if (axu.getId() == null) {
            tipoUsuario.setCodigo(this.obtenerCodigo());
            tipoUsuario.setFlag(1);
            this.ds.save(tipoUsuario);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    public Boolean add(TipoUsuario tipoUsuario) {
        Boolean exito = Boolean.FALSE;
        if (tipoUsuario.getOpcionesSistema().isEmpty()) {
            this.ds.save(tipoUsuario);
            exito = Boolean.TRUE;
        } else {
            updateList(tipoUsuario);
        }
        return exito;
    }

    public Boolean updateList(TipoUsuario tipoUsuario) {
        Query<TipoUsuario> query = this.ds.createQuery(TipoUsuario.class);
        query.and(
                query.criteria("codigo").equal(tipoUsuario.getCodigo())
        );
        UpdateOperations<TipoUsuario> update = this.ds.createUpdateOperations(TipoUsuario.class);
        update.set("opcionesSistema", tipoUsuario.getOpcionesSistema());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    private Integer obtenerCodigo() {
        List<TipoUsuario> tipoUsuarios = this.ds.find(TipoUsuario.class).asList();
        if (tipoUsuarios == null) {
            tipoUsuarios = new ArrayList<>();
        }
        Integer size = tipoUsuarios.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<TipoUsuario> obtenerLista() {
        List<TipoUsuario> tipoUsuarios = this.ds.find(TipoUsuario.class).asList();
        return tipoUsuarios;
    }

    public List<TipoUsuario> obtenerListFlag(Integer flag) {
        List<TipoUsuario> list = new ArrayList<>();
        Query<TipoUsuario> result = this.ds.find(TipoUsuario.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }
    
    public List<String> obtenerUrlsTipoUsuario(Integer codigo) {
        List<String> urls = new ArrayList<>();
        TipoUsuario tipo = this.findByCodigo(codigo);
        List<OpcionSistema> list = tipo.getOpcionesSistema();
        for (OpcionSistema opc : list) {
            urls.add(opc.getMenuitem_outcome() + ".xhtml");
        }
        urls.add("/faces/views/perfil.xhtml");
        urls.add("/faces/views/betel.xhtml");
        urls.add("/faces/index.xhtml");
        urls.add("/faces/loginPage");
        urls.add("/");
        return urls;
    }

    public TipoUsuario findByCodigo(TipoUsuario tipoUsuario) {
        TipoUsuario find = new TipoUsuario();
        Query<TipoUsuario> result = this.ds.find(TipoUsuario.class).
                field("codigo").equal(tipoUsuario.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public TipoUsuario findByCodigo(Integer tipoUsuario) {
        TipoUsuario find = new TipoUsuario();
        Query<TipoUsuario> result = this.ds.find(TipoUsuario.class).
                field("codigo").equal(tipoUsuario).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
    
    public TipoUsuario findByNombre(TipoUsuario tipoUsuario) {
        TipoUsuario find = new TipoUsuario();
        Query<TipoUsuario> result = this.ds.find(TipoUsuario.class).
                field("nombre").equal(tipoUsuario.getNombre()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
   
    public void delete(TipoUsuario tipoUsuario) {
        this.ds.delete(tipoUsuario);
    }

    public Boolean deteleFlag(TipoUsuario tipoUsuario) {
        Query<TipoUsuario> query = this.ds.createQuery(TipoUsuario.class);
        tipoUsuario.setFlag(0);
        query.and(
                query.criteria("codigo").equal(tipoUsuario.getCodigo())
        );
        UpdateOperations<TipoUsuario> update = this.ds.createUpdateOperations(TipoUsuario.class);
        update.set("flag", tipoUsuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(TipoUsuario tipoUsuario) {
        Query<TipoUsuario> query = this.ds.createQuery(TipoUsuario.class);
        query.and(
                query.criteria("codigo").equal(tipoUsuario.getCodigo())
        );
        UpdateOperations<TipoUsuario> update = this.ds.createUpdateOperations(TipoUsuario.class);
        update.set("nombre", tipoUsuario.getNombre()).
                set("admin", tipoUsuario.getAdmin()).
                set("opcionesSistema", tipoUsuario.getOpcionesSistema()).
                set("username",tipoUsuario.getUsername()).
                set("flag", tipoUsuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

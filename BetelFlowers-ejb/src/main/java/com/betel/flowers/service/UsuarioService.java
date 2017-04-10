/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.Usuario;
import com.mongo.persistance.MongoPersistence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.DigestUtils;
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
public class UsuarioService implements Serializable {

    private static final long serialVersionUID = 7359478675389151589L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(Usuario usuario) {
        Boolean exito = Boolean.FALSE;
        Usuario axu = this.findByCodigo(usuario);
        if (axu.getId() == null) {
            usuario.setUsername(usuario.getUsername().trim());
            usuario.setCodigo(this.obtenerCodigo());
            usuario.setPassword(DigestUtils.md5Hex(usuario.getPassword()));
            usuario.setFlag(1);
            this.ds.save(usuario);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<Usuario> usuarios = this.ds.find(Usuario.class).asList();
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }
        Integer size = usuarios.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public void add(Usuario usuario) {
        if (usuario.getTipoUsuario() != null) {
            this.ds.save(usuario);
        }
    }

    public Boolean existsUsername(Usuario usuario) {
        Boolean find = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            find = Boolean.TRUE;
        }
        return find;
    }

    public Boolean stateUsername(Usuario usuario) {
        Boolean state = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            state = user.getEstado();
        }
        return state;
    }

    public Boolean checkPassword(Usuario usuario) {
        Boolean password = Boolean.FALSE;
        Usuario user = this.findByUsername(usuario);
        if (user.getId() != null) {
            String inputPasswordUser = DigestUtils.md5Hex(usuario.getPassword());
            if (user.getPassword().equals(inputPasswordUser)) {
                password = Boolean.TRUE;
            }
        }
        return password;
    }

    public List<Usuario> obtenerLista() {
        List<Usuario> usuarios = this.ds.find(Usuario.class).asList();
        return usuarios;
    }

    public List<Usuario> obtenerListFlag(Integer flag) {
        List<Usuario> list = new ArrayList<>();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public Usuario findByCodigo(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("codigo").equal(usuario.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findByCodigo(Integer usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("codigo").equal(usuario).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public Usuario findByUsername(Usuario usuario) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("username").equal(usuario.getUsername()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }
   
    public Usuario findByEmail(String email) {
        Usuario find = new Usuario();
        Query<Usuario> result = this.ds.find(Usuario.class).
                field("infoPersonal.email").equal(email).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(Usuario usuario) {
        this.ds.delete(usuario);
    }

    public Boolean deteleFlag(Usuario usuario) {
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        usuario.setFlag(0);
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("flag", usuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(Usuario usuario) {
        Query<Usuario> query = this.ds.createQuery(Usuario.class);
        query.and(
                query.criteria("codigo").equal(usuario.getCodigo())
        );
        UpdateOperations<Usuario> update = this.ds.createUpdateOperations(Usuario.class);
        update.set("username", usuario.getUsername()).
                set("password", usuario.getPassword()).
                set("estado", usuario.getEstado()).
                set("tipoUsuario", usuario.getTipoUsuario()).
                set("infoPersonal", usuario.getInfoPersonal()).
                set("flag", usuario.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

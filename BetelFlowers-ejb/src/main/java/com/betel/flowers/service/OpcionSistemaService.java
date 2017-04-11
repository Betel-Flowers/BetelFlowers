/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.service;

import com.betel.flowers.model.OpcionSistema;
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
public class OpcionSistemaService implements Serializable {

    private static final long serialVersionUID = 6611791515208970477L;

    private MongoPersistence conn = new MongoPersistence();
    private Datastore ds = conn.context();

    public Boolean insert(OpcionSistema opcionSistema) {
        Boolean exito = Boolean.FALSE;
        OpcionSistema axu = this.findByCodigo(opcionSistema);
        if (axu.getId() == null) {
            opcionSistema.setCodigo(this.obtenerCodigo());
            opcionSistema.setFlag(1);
            this.ds.save(opcionSistema);
            exito = Boolean.TRUE;
        }
        return exito;
    }

    private Integer obtenerCodigo() {
        List<OpcionSistema> opcionSistemas = this.ds.find(OpcionSistema.class).asList();
        if (opcionSistemas == null) {
            opcionSistemas = new ArrayList<>();
        }
        Integer size = opcionSistemas.size();
        Integer number = 1000 + 1 * size;
        return number;
    }

    public List<OpcionSistema> obtenerLista() {
        List<OpcionSistema> opcionSistemas = this.ds.find(OpcionSistema.class).asList();
        return opcionSistemas;
    }

    public List<OpcionSistema> obtenerListFlag(Integer flag) {
        List<OpcionSistema> list = new ArrayList<>();
        Query<OpcionSistema> result = this.ds.find(OpcionSistema.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
        }
        return list;
    }

    public List<String> obtenerUrlsAdmin() {
        List<String> urls = new ArrayList<>();
        List<OpcionSistema> list = this.obtenerListFlag(1);
        for (OpcionSistema opc : list) {
            urls.add(opc.getMenuitem_outcome() + ".xhtml");
        }
        urls.add("/faces/views/betel/template.xhtml");
        urls.add("/faces/views/usuario.xhtml");
        urls.add("/faces/views/tipousuario.xhtml");
        urls.add("/faces/views/opcionsistema.xhtml");
        urls.add("/faces/views/betel.xhtml");
        urls.add("/faces/register.xhtml");
        urls.add("/faces/index.xhtml");
        urls.add("/faces/loginPage");
        urls.add("/");
        return urls;
    }

    public List<OpcionSistema> obtenerListFlagSelectOptionMenu(Integer flag) {
        List<OpcionSistema> list = new ArrayList<>();
        Query<OpcionSistema> result = this.ds.find(OpcionSistema.class).
                field("flag").equal(flag);
        if (result.asList() != null && !result.asList().isEmpty()) {
            list = result.asList();
            list = this.selectSubmenuLabel(list);
        }
        return list;
    }

    private List<OpcionSistema> selectSubmenuLabel(List<OpcionSistema> menu) {

        List<OpcionSistema> unique = new ArrayList<>();
        if (menu != null && !menu.isEmpty()) {
            unique.add(menu.get(0));
            for (int i = 0; i < menu.size(); i++) {
                if (!(menu.get(i).getSubmenu_label().equals(unique.get(unique.size() - 1).getSubmenu_label()))) {
                    unique.add(menu.get(i));
                }
            }
        }
        return unique;
    }

    public OpcionSistema findByCodigo(OpcionSistema opcionSistema) {
        OpcionSistema find = new OpcionSistema();
        Query<OpcionSistema> result = this.ds.find(OpcionSistema.class).
                field("codigo").equal(opcionSistema.getCodigo()).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public OpcionSistema findByCodigo(Integer opcionSistema) {
        OpcionSistema find = new OpcionSistema();
        Query<OpcionSistema> result = this.ds.find(OpcionSistema.class).
                field("codigo").equal(opcionSistema).
                field("flag").equal(1);
        if (result.asList() != null && !result.asList().isEmpty()) {
            find = result.asList().get(0);
        }
        return find;
    }

    public void delete(OpcionSistema opcionSistema) {
        this.ds.delete(opcionSistema);
    }

    public Boolean deteleFlag(OpcionSistema opcionSistema) {
        Query<OpcionSistema> query = this.ds.createQuery(OpcionSistema.class);
        opcionSistema.setFlag(0);
        query.and(
                query.criteria("codigo").equal(opcionSistema.getCodigo())
        );
        UpdateOperations<OpcionSistema> update = this.ds.createUpdateOperations(OpcionSistema.class);
        update.set("flag", opcionSistema.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }

    public Boolean update(OpcionSistema opcionSistema) {
        Query<OpcionSistema> query = this.ds.createQuery(OpcionSistema.class);
        query.and(
                query.criteria("codigo").equal(opcionSistema.getCodigo())
        );
        UpdateOperations<OpcionSistema> update = this.ds.createUpdateOperations(OpcionSistema.class);
        update.set("submenu_label", opcionSistema.getSubmenu_label()).
                set("menuitem_value", opcionSistema.getMenuitem_value()).
                set("menuitem_outcome", opcionSistema.getMenuitem_outcome()).
                set("flag", opcionSistema.getFlag());
        UpdateResults results = this.ds.update(query, update);
        return results.getUpdatedExisting();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 *
 * @author luis
 */
@Entity(value = "OpcionSistema", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class OpcionSistema extends BaseEntity{
    
    private Integer codigo;
    private String submenu_label;
    private String menuitem_value;
    private String menuitem_outcome;
    private Integer flag;

    public OpcionSistema() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
   
    public String getSubmenu_label() {
        return submenu_label;
    }

    public void setSubmenu_label(String submenu_label) {
        this.submenu_label = submenu_label;
    }

    public String getMenuitem_value() {
        return menuitem_value;
    }

    public void setMenuitem_value(String menuitem_value) {
        this.menuitem_value = menuitem_value;
    }

    public String getMenuitem_outcome() {
        return menuitem_outcome;
    }

    public void setMenuitem_outcome(String menuitem_outcome) {
        this.menuitem_outcome = menuitem_outcome;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OpcionSistema other = (OpcionSistema) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OpcionSistema{" + "codigo=" + codigo + ", submenu_label=" + submenu_label + ", menuitem_value=" + menuitem_value + ", menuitem_outcome=" + menuitem_outcome + '}';
    }
    
}

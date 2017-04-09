/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "Usuario", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Usuario extends BaseEntity {

    private Integer codigo;
    private String username;
    private String password;
    private Boolean estado;
    private Integer flag;

    @Reference
    private TipoUsuario tipoUsuario;
    @Embedded
    private InformacionPersonal infoPersonal;

    public Usuario() {
        this.infoPersonal = new InformacionPersonal();
        this.estado = Boolean.FALSE;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public InformacionPersonal getInfoPersonal() {
        return infoPersonal;
    }

    public void setInfoPersonal(InformacionPersonal infoPersonal) {
        this.infoPersonal = infoPersonal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.codigo);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codigo=" + codigo + ", username=" + username + ", password=" + password + ", estado=" + estado + ", flag=" + flag + ", tipoUsuario=" + tipoUsuario + ", infoPersonal=" + infoPersonal + '}';
    }

}

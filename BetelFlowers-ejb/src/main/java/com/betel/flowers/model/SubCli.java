/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import javax.persistence.Embeddable;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embeddable
public class SubCli {

    @Reference
    private SubCliente subCliente;

    public SubCli() {
        this.subCliente = new SubCliente();
    }

    public SubCliente getSubCliente() {
        return subCliente;
    }

    public void setSubCliente(SubCliente subCliente) {
        this.subCliente = subCliente;
    }

}

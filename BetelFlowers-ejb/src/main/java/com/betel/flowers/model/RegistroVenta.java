/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
public class RegistroVenta {
    
    @Reference
    Cliente cliente;
    @Reference
    SubCliente subCliente;
    @Reference
    private Ciudad puertoEmbarque;
    @Reference
    private Ciudad puertoDestino;
    @Reference
    private Carguera agenciaCarga;
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class ItemVenta {
    
    Integer cantidad;
    Double precio;
    @Reference
    Variedad variedad;
    @Reference
    TipoCaja tipoCaja;
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util.menu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class SubMenu {
    
    private String label;
    private List<ItemMenu> items;

    public SubMenu() {
        this.label = "";
        this.items = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ItemMenu> getItems() {
        return items;
    }

    public void setItems(List<ItemMenu> items) {
        this.items = items;
    }
    
}

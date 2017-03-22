/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.util;

/**
 *
 * @author luis
 */
public class StateBeanUtil {

    private Boolean insert;
    private Boolean update;
    private Boolean delete;

    public StateBeanUtil() {
        this.insert = Boolean.FALSE;
        this.update = Boolean.TRUE;
        this.delete = Boolean.TRUE;
    }

    public void addState() {
        this.insert = Boolean.FALSE;
        this.update = Boolean.TRUE;
        this.delete = Boolean.TRUE;
    }

    public void modifyState() {
        this.insert = Boolean.TRUE;
        this.update = Boolean.FALSE;
        this.delete = Boolean.FALSE;
    }

    public void allBlock() {
        this.insert = Boolean.TRUE;
        this.update = Boolean.TRUE;
        this.delete = Boolean.TRUE;
    }

    public Boolean getInsert() {
        return insert;
    }

    public void setInsert(Boolean insert) {
        this.insert = insert;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

}

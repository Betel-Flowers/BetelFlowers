/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Variedad;
import java.io.Serializable;

/**
 *
 * @author luis
 */
public class Malla implements Serializable {

    private static final long serialVersionUID = 5682312087842209261L;

    private Variedad variedad;
    private PointMatrix p20;
    private PointMatrix p25;
    private PointMatrix p30;
    private PointMatrix p35;
    private PointMatrix p40;
    private PointMatrix p45;
    private PointMatrix p50;
    private PointMatrix p55;
    private PointMatrix p60;
    private PointMatrix p65;
    private PointMatrix p70;
    private PointMatrix p75;
    private PointMatrix p80;
    private PointMatrix p85;
    private PointMatrix p90;
    private PointMatrix p95;
    private PointMatrix p100;

    public Malla() {
        this.variedad = new Variedad();
        this.p20 = new PointMatrix();
        this.p25 = new PointMatrix();
        this.p30 = new PointMatrix();
        this.p35 = new PointMatrix();
        this.p40 = new PointMatrix();
        this.p45 = new PointMatrix();
        this.p50 = new PointMatrix();
        this.p55 = new PointMatrix();
        this.p60 = new PointMatrix();
        this.p65 = new PointMatrix();
        this.p70 = new PointMatrix();
        this.p75 = new PointMatrix();
        this.p80 = new PointMatrix();
        this.p85 = new PointMatrix();
        this.p90 = new PointMatrix();
        this.p95 = new PointMatrix();
        this.p100 = new PointMatrix();

    }

    public Malla(Variedad variedad) {
        this.variedad = variedad;
        this.p20 = new PointMatrix();
        this.p25 = new PointMatrix();
        this.p30 = new PointMatrix();
        this.p35 = new PointMatrix();
        this.p40 = new PointMatrix();
        this.p45 = new PointMatrix();
        this.p50 = new PointMatrix();
        this.p55 = new PointMatrix();
        this.p60 = new PointMatrix();
        this.p65 = new PointMatrix();
        this.p70 = new PointMatrix();
        this.p75 = new PointMatrix();
        this.p80 = new PointMatrix();
        this.p85 = new PointMatrix();
        this.p90 = new PointMatrix();
        this.p95 = new PointMatrix();
        this.p100 = new PointMatrix();
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public PointMatrix getP20() {
        return p20;
    }

    public void setP20(PointMatrix p20) {
        this.p20 = p20;
    }

    public PointMatrix getP25() {
        return p25;
    }

    public void setP25(PointMatrix p25) {
        this.p25 = p25;
    }

    public PointMatrix getP30() {
        return p30;
    }

    public void setP30(PointMatrix p30) {
        this.p30 = p30;
    }

    public PointMatrix getP35() {
        return p35;
    }

    public void setP35(PointMatrix p35) {
        this.p35 = p35;
    }

    public PointMatrix getP40() {
        return p40;
    }

    public void setP40(PointMatrix p40) {
        this.p40 = p40;
    }

    public PointMatrix getP45() {
        return p45;
    }

    public void setP45(PointMatrix p45) {
        this.p45 = p45;
    }

    public PointMatrix getP50() {
        return p50;
    }

    public void setP50(PointMatrix p50) {
        this.p50 = p50;
    }

    public PointMatrix getP55() {
        return p55;
    }

    public void setP55(PointMatrix p55) {
        this.p55 = p55;
    }

    public PointMatrix getP60() {
        return p60;
    }

    public void setP60(PointMatrix p60) {
        this.p60 = p60;
    }

    public PointMatrix getP65() {
        return p65;
    }

    public void setP65(PointMatrix p65) {
        this.p65 = p65;
    }

    public PointMatrix getP70() {
        return p70;
    }

    public void setP70(PointMatrix p70) {
        this.p70 = p70;
    }

    public PointMatrix getP75() {
        return p75;
    }

    public void setP75(PointMatrix p75) {
        this.p75 = p75;
    }

    public PointMatrix getP80() {
        return p80;
    }

    public void setP80(PointMatrix p80) {
        this.p80 = p80;
    }

    public PointMatrix getP85() {
        return p85;
    }

    public void setP85(PointMatrix p85) {
        this.p85 = p85;
    }

    public PointMatrix getP90() {
        return p90;
    }

    public void setP90(PointMatrix p90) {
        this.p90 = p90;
    }

    public PointMatrix getP95() {
        return p95;
    }

    public void setP95(PointMatrix p95) {
        this.p95 = p95;
    }

    public PointMatrix getP100() {
        return p100;
    }

    public void setP100(PointMatrix p100) {
        this.p100 = p100;
    }

}

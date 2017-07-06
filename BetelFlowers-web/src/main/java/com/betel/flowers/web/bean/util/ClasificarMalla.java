/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.MatrizDisponibilidad;
import com.betel.flowers.model.NodoDisponibilidad;
import com.betel.flowers.model.ValorNodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ClasificarMalla implements Serializable {

    private static final long serialVersionUID = -3632171759644997376L;

    private List<Malla> mallas;
    private MatrizDisponibilidad matriz;
    private List<PointMatrix> points;

    public ClasificarMalla() {
        this.mallas = new ArrayList<>();
        this.matriz = new MatrizDisponibilidad();
        this.points = new ArrayList<>();
    }

    public void clasificar() {
        this.matriz.calcularColumms();
        if (!this.matriz.getColummsLongitud().isEmpty()) {
            for (NodoDisponibilidad node : this.matriz.getVariedades()) {
                this.mallas.add(new Malla(node.getVariedad()));
                for (ValorNodo value : node.getValoresNodo()) {
                    this.points.add(new PointMatrix(node.getVariedad().getNombre(),
                            value.getLongitud() + "",
                            0,
                            value));
                }
            }
        }
        for (int i = 0; i < this.mallas.size(); i++) {
            for (int j = 0; j < this.points.size(); j++) {
                if (this.mallas.get(i).getVariedad().getNombre().equals(this.points.get(j).getVariedad())) {
                    switch (this.points.get(j).getGradoLogitud()) {
                        case "20":
                            this.mallas.get(i).setP20(this.points.get(j));
                            break;
                        case "25":
                            this.mallas.get(i).setP25(this.points.get(j));
                            break;
                        case "30":
                            this.mallas.get(i).setP30(this.points.get(j));
                            break;
                        case "35":
                            this.mallas.get(i).setP35(this.points.get(j));
                            break;
                        case "40":
                            this.mallas.get(i).setP40(this.points.get(j));
                            break;
                        case "45":
                            this.mallas.get(i).setP45(this.points.get(j));
                            break;
                        case "50":
                            this.mallas.get(i).setP50(this.points.get(j));
                            break;
                        case "55":
                            this.mallas.get(i).setP55(this.points.get(j));
                            break;
                        case "60":
                            this.mallas.get(i).setP60(this.points.get(j));
                            break;
                        case "65":
                            this.mallas.get(i).setP65(this.points.get(j));
                            break;
                        case "70":
                            this.mallas.get(i).setP70(this.points.get(j));
                            break;
                        case "75":
                            this.mallas.get(i).setP75(this.points.get(j));
                            break;
                        case "80":
                            this.mallas.get(i).setP80(this.points.get(j));
                            break;
                        case "85":
                            this.mallas.get(i).setP85(this.points.get(j));
                            break;
                        case "90":
                            this.mallas.get(i).setP90(this.points.get(j));
                            break;
                        case "95":
                            this.mallas.get(i).setP95(this.points.get(j));
                            break;
                        case "100":
                            this.mallas.get(i).setP100(this.points.get(j));
                            break;
                        default:
                    }
                }
            }
        }
    }

    public void setMatriz(MatrizDisponibilidad matriz) {
        this.matriz = matriz;
    }

    public List<Malla> getMallas() {
        return mallas;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.Cliente;
import com.betel.flowers.model.Especie;
import com.betel.flowers.model.MatrizDisponibilidad;
import com.betel.flowers.model.PointMatrix;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.model.RegistroVenta;
import com.betel.flowers.model.SubCliente;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.ClienteService;
import com.betel.flowers.service.EspecieService;
import com.betel.flowers.service.RegistroExportacionService;
import com.betel.flowers.service.RegistroVentaService;
import com.betel.flowers.web.bean.util.ClasificarMalla;
import com.betel.flowers.web.bean.util.Malla;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "registroVentaBean")
@ViewScoped
public class RegistroVentaBean implements Serializable {

    private static final long serialVersionUID = 630861283476011721L;

    private RegistroVenta nuevo;
    //CLIENTE
    private Cliente selectedCliente;
    private SubCliente selectedSubCliente;
    private Boolean selectedSubCli;
    private List<Cliente> clientes;
    private List<Cliente> filteredClientes;
    private List<SubCliente> subClientes;
    private List<SubCliente> filteredSubClientes;
    //STOCK'S REGISTRO EXPORTACION
    private RegistroExportacion findStocksExportacion;
    //MATRIX
    private MatrizDisponibilidad dataMatrix;
    private List<Malla> malla;
    private List<PointMatrix> selectDataMatrix;
    private PointMatrix selectPointMatrix;
    //CAJAS
    private int co;
    private int cf;
    //SKIP
    private Boolean skip;

    @Inject
    private ClienteService clienteService;

    @Inject
    private RegistroExportacionService registroExportacionservice;
    @Inject
    private EspecieService especieService;
    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private RegistroVentaService ventaService;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroVenta();
        this.nuevo.setUsername("usertest");
        this.selectedCliente = null;
        this.selectedSubCliente = null;
        this.selectedSubCli = Boolean.FALSE;
        this.filteredClientes = null;
        this.filteredSubClientes = null;
        this.clientes = this.clienteService.obtenerListFlag(1);
        this.subClientes = new ArrayList<>();
        this.findStocksExportacion = new RegistroExportacion();
        this.skip = Boolean.FALSE;
        this.dataMatrix = new MatrizDisponibilidad();
        this.malla = new ArrayList<>();
        this.selectPointMatrix = null;
        this.selectDataMatrix = new ArrayList<>();
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
            this.subClientes = new ArrayList<>();
        }
    }

    public void saveVenta(ActionEvent evt) {
        if (this.nuevo != null) {
            this.nuevo.setCliente(this.selectedCliente);
            this.nuevo.setSubCliente(this.selectedSubCliente);
            this.nuevo.setMatrixVenta(this.selectDataMatrix);
            this.nuevo.setNumeroCajas(this.co);
            this.nuevo.setSubTotal(calcularSubTotal());
            if (!this.selectedSubCli) {
                this.nuevo.setSubCliente(null);
            }
            Boolean exito = this.ventaService.insert(this.nuevo);
            if (exito) {
                this.init();
            }
        }
    }

    public void addDetailPointMatrix(PointMatrix px, int mxi) {
        if (this.co == 0) {
            this.co++;
        }
        if (px.getValue() != 0) {
            Integer value = px.getValue();
            Integer cantidad = px.getValorNodo().getCantidad();
            PointMatrix point = new PointMatrix();
            px.getValorNodo().setCantidad(cantidad - value);
            String code = this.selectedCliente.getCodigo() + "" + px.getVariadad().getCodigo() + "" + value + "" + px.getGradoLogitud() + "" + co + "" + cf;
            code = code.replaceAll("BETEL", "");
            code = code.replace("-", "");
            if (mxi >= 0 && mxi < this.malla.size()) {
                this.cf++;
                point.setVariadad(px.getVariadad());
                point.setGradoLogitud(px.getGradoLogitud());
                point.setValue(value);
                point.getValorNodo().setCantidad(px.getValorNodo().getCantidad());
                point.getValorNodo().setGlongitud(px.getValorNodo().getGlongitud());
                point.getValorNodo().setLongitud(px.getValorNodo().getLongitud());
                point.getValorNodo().setRegistros(px.getValorNodo().getRegistros());
                point.setPrecioMin(px.getPrecioMin());
                point.setNumeroTallosRamo(this.findStocksExportacion.getNumeroTallosRamo());
                point.setCo(this.co);
                point.setCf(this.cf);
                point.setCodeMark(code);
                this.selectDataMatrix.add(point);
                this.malla.get(mxi).updatePoint(px);
            }
        }
    }

    public Double calcularSubTotal() {
        Double total = 0.0;
        if (this.selectDataMatrix != null && !this.selectDataMatrix.isEmpty()) {
            for (PointMatrix po : this.selectDataMatrix) {
                total = total + po.getSubTotal();
            }
        }
        return total;
    }

    public void deleteItemDetail(ActionEvent evt, PointMatrix point) {
        this.selectPointMatrix = point;
        if (this.selectPointMatrix != null) {
            Boolean exito = this.selectDataMatrix.remove(this.selectPointMatrix);
            if (exito) {
                this.selectPointMatrix = null;
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedCliente = (Cliente) event.getObject();
        if (this.selectedCliente != null) {
            this.subClientes = this.selectedCliente.getSubClientes();
            this.nuevo.setCliente(this.selectedCliente);
        }
    }

    public void onRowSelectSubCliente(SelectEvent event) {
        this.selectedSubCliente = (SubCliente) event.getObject();
        if (this.selectedSubCliente != null) {
            this.nuevo.setSubCliente(this.selectedSubCliente);
            this.selectedSubCli = Boolean.TRUE;
            this.nuevo.setAddSubCli(this.selectedSubCli);
        }
    }

    public void findDisponibilidadStock(ActionEvent evt) {
        BodegaVirtual bodega = this.bodegaService.findByCodigo(this.findStocksExportacion.getBodega());
        Especie especie = this.especieService.findByCodigo(this.findStocksExportacion.getVariedad().getEspecie());
        this.findStocksExportacion.setBodega(bodega);
        this.findStocksExportacion.getVariedad().setEspecie(especie);
        this.dataMatrix = new MatrizDisponibilidad();
        this.malla = new ArrayList<>();
        this.dataMatrix = this.registroExportacionservice.obtenerMatrixDisponibilidadFlag(this.findStocksExportacion, 1);
        //PROCESO NUEVO
        if (this.dataMatrix != null) {
            ClasificarMalla clasf = new ClasificarMalla();
            clasf.setMatriz(dataMatrix);
            clasf.clasificar();
            this.malla = clasf.getMallas();
        }
    }

    public List<String> extractPuntosCorteNode(List<RegistroExportacion> registros, Variedad variedad) {
        List<String> list = new ArrayList<>();
        for (RegistroExportacion item : registros) {
            list.add(item.getPuntoCorte());
        }
        list = this.uniqueString(list);
        if (list.isEmpty()) {
            list = variedad.getPuntosCorte();
        }
        return list;
    }

    public void newCajaMark(ActionEvent evt) {
        this.co++;
        this.cf = 0;
    }

    private List<String> uniqueString(List<String> lista) {
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        return lista;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;
            return "venta";
        } else {
            return event.getNewStep();
        }
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getFilteredClientes() {
        return filteredClientes;
    }

    public void setFilteredClientes(List<Cliente> filteredClientes) {
        this.filteredClientes = filteredClientes;
    }

    public List<SubCliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<SubCliente> subClientes) {
        this.subClientes = subClientes;
    }

    public SubCliente getSelectedSubCliente() {
        return selectedSubCliente;
    }

    public void setSelectedSubCliente(SubCliente selectedSubCliente) {
        this.selectedSubCliente = selectedSubCliente;
    }

    public List<SubCliente> getFilteredSubClientes() {
        return filteredSubClientes;
    }

    public void setFilteredSubClientes(List<SubCliente> filteredSubClientes) {
        this.filteredSubClientes = filteredSubClientes;
    }

    public RegistroVenta getNuevo() {
        return nuevo;
    }

    public void setNuevo(RegistroVenta nuevo) {
        this.nuevo = nuevo;
    }

    public RegistroExportacion getFindStocksExportacion() {
        return findStocksExportacion;
    }

    public void setFindStocksExportacion(RegistroExportacion findStocksExportacion) {
        this.findStocksExportacion = findStocksExportacion;
    }

    public Boolean getSkip() {
        return skip;
    }

    public void setSkip(Boolean skip) {
        this.skip = skip;
    }

    public MatrizDisponibilidad getDataMatrix() {
        return dataMatrix;
    }

    public void setDataMatrix(MatrizDisponibilidad dataMatrix) {
        this.dataMatrix = dataMatrix;
    }

    public List<Malla> getMalla() {
        return malla;
    }

    public void setMalla(List<Malla> malla) {
        this.malla = malla;
    }

    public List<PointMatrix> getSelectDataMatrix() {
        return selectDataMatrix;
    }

    public void setSelectDataMatrix(List<PointMatrix> selectDataMatrix) {
        this.selectDataMatrix = selectDataMatrix;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getCf() {
        return cf;
    }

    public void setCf(int cf) {
        this.cf = cf;
    }

    public PointMatrix getSelectPointMatrix() {
        return selectPointMatrix;
    }

    public void setSelectPointMatrix(PointMatrix selectPointMatrix) {
        this.selectPointMatrix = selectPointMatrix;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.Carguera;
import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.Cliente;
import com.betel.flowers.model.CuartoFrioCarguera;
import com.betel.flowers.model.Dae;
import com.betel.flowers.model.DetalleVenta;
import com.betel.flowers.model.Especie;
import com.betel.flowers.model.ItemVariedadVentaEmpaque;
import com.betel.flowers.model.MatrizDisponibilidad;
import com.betel.flowers.model.Pais;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.model.RegistroVenta;
import com.betel.flowers.model.SubCliente;
import com.betel.flowers.model.TerminoExportacion;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.CargueraService;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.ClienteService;
import com.betel.flowers.service.CuartoFrioCargueraService;
import com.betel.flowers.service.DaeService;
import com.betel.flowers.service.EspecieService;
import com.betel.flowers.service.MarcaCajaService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.RegistroExportacionService;
import com.betel.flowers.service.TerminoExportacionService;
import com.betel.flowers.service.TipoCajaService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.ClasificarMalla;
import com.betel.flowers.web.bean.util.Malla;
import com.betel.flowers.web.bean.util.PointMatrix;
import com.betel.flowers.web.bean.util.RegistroDetalleVenta;
import com.betel.flowers.web.util.FacesUtil;
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
    private List<Cliente> clientes;
    private List<Cliente> filteredClientes;
    private List<SubCliente> subClientes;
    private List<SubCliente> filteredSubClientes;
    private List<Ciudad> origen;
    private List<Ciudad> destino;
    //AGENCIA DE CARGA
    private List<CuartoFrioCarguera> frios;
    //STOCK'S REGISTRO EXPORTACION
    private RegistroExportacion findStocksExportacion;
    private List<ItemVariedadVentaEmpaque> variedadesCaja;
    private RegistroDetalleVenta detalleVenta;
    //MATRIX
    private MatrizDisponibilidad dataMatrix;
    private List<Malla> malla;
    private List<PointMatrix> selectDataMatrix;
    //CAJAS
    private int co;
    private int cf;
    //SKIP
    private Boolean skip;

    @Inject
    private ClienteService clienteService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private DaeService daeService;
    @Inject
    private TerminoExportacionService terminoService;
    @Inject
    private CargueraService carqueraService;
    @Inject
    private CuartoFrioCargueraService frioService;
    @Inject
    private RegistroExportacionService registroExportacionservice;
    @Inject
    private VariedadService variedadService;
    @Inject
    private EspecieService especieService;
    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private TipoCajaService tipoCajaService;
    @Inject
    private MarcaCajaService marcaCajaService;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroVenta();
        this.nuevo.setUsername("usertest");
        this.selectedCliente = null;
        this.selectedSubCliente = null;
        this.filteredClientes = null;
        this.filteredSubClientes = null;
        this.clientes = this.clienteService.obtenerListFlag(1);
        this.subClientes = new ArrayList<>();
        this.origen = new ArrayList<>();
        this.destino = new ArrayList<>();
        this.frios = new ArrayList<>();
        this.findStocksExportacion = new RegistroExportacion();
        this.detalleVenta = new RegistroDetalleVenta();
        this.skip = Boolean.FALSE;
        this.variedadesCaja = new ArrayList<>();
        this.dataMatrix = new MatrizDisponibilidad();
        this.malla = new ArrayList<>();
        this.selectDataMatrix = new ArrayList<>();
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
            this.subClientes = new ArrayList<>();
        }
    }

    public void addDetailPointMatrix(PointMatrix px, int mxi) {
        if (this.co == 0) {
            this.co++;
        }
        if (px.getValue() != 0) {
            Integer value = px.getValue();
            Integer cantidad = px.getValorNodo().getCantidad();
            this.selectDataMatrix.add(px);
            px.getValorNodo().setCantidad(cantidad - value);
            if (mxi >= 0 && mxi < this.malla.size()) {
                this.cf++;
                px.setNumeroTallosRamo(this.findStocksExportacion.getNumeroTallosRamo());
                px.setCo(this.co);
                px.setCf(this.cf);
                this.malla.get(mxi).updatePoint(px);
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
        }
    }

    public void findDaeByPaisDestino() {
        Pais pais = this.paisService.findByCodigo(this.nuevo.getPuertoDestino().getPais());
        Dae dae = this.daeService.findByPais(pais);
        this.nuevo.setDae(dae);
        this.destino = this.ciudadService.obtenerListPais(pais);
        if (this.destino == null) {
            this.destino = new ArrayList<>();
        }
    }

    public void chancePaisOrigen() {
        Pais pais = this.paisService.findByCodigo(this.nuevo.getPuertoEmbarque().getPais());
        this.origen = this.ciudadService.obtenerListPais(pais);
        if (this.origen == null) {
            this.origen = new ArrayList<>();
        }
    }

    public void findTerminoExportacion() {
        TerminoExportacion termino = this.terminoService.findByCodigo(this.nuevo.getTermino());
        this.nuevo.setTermino(termino);
    }

    public void changeAgenciaCarga() {
        Carguera carguera = this.carqueraService.findByCodigo(this.nuevo.getAgenciaCarga());
        this.nuevo.setAgenciaCarga(carguera);
        this.frios = this.frioService.obtenerListBodega(carguera.getBodega());
        if (this.frios == null) {
            this.frios = new ArrayList<>();
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

    public void addCajasToDetalle(ActionEvent evt) {
        if (this.variedadesCaja != null && !this.variedadesCaja.isEmpty()) {
            this.detalleVenta.getNuevo().setDetalleCajaVenta(this.variedadesCaja);
            Integer codeTpCaja = this.detalleVenta.getNuevo().getCajaTipo().getCodigo();
            Integer codeMrCaja = this.detalleVenta.getNuevo().getMarcaCaja().getCodigo();
            this.detalleVenta.getNuevo().setCajaTipo(this.tipoCajaService.findByCodigo(codeTpCaja));
            this.detalleVenta.getNuevo().setMarcaCaja(this.marcaCajaService.findByCodigo(codeMrCaja));
            this.detalleVenta.add(evt);
            this.variedadesCaja = new ArrayList<>();
            this.detalleVenta.setNuevo(new DetalleVenta());
        }
    }

    public List<ItemVariedadVentaEmpaque> subListCajaVariedades(DetalleVenta subList) {
        List<ItemVariedadVentaEmpaque> list = new ArrayList<>();
        if (subList.getDetalleCajaVenta() != null && !subList.getDetalleCajaVenta().isEmpty()) {
            list = subList.getDetalleCajaVenta();
        }
        return list;
    }

    public List<String> extractPuntosCorteNode(List<RegistroExportacion> registros) {
        List<String> list = new ArrayList<>();
        Variedad variedad = new Variedad();
        for (RegistroExportacion item : registros) {
            list.add(item.getPuntoCorte());
            variedad = item.getVariedad();
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

    public void removeItemVariedadCaja(ActionEvent evt, ItemVariedadVentaEmpaque select) {
        if (select != null
                && this.variedadesCaja != null
                && !this.variedadesCaja.isEmpty()) {
            Boolean exito = this.variedadesCaja.remove(select);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
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

    public List<Ciudad> getOrigen() {
        return origen;
    }

    public void setOrigen(List<Ciudad> origen) {
        this.origen = origen;
    }

    public List<Ciudad> getDestino() {
        return destino;
    }

    public void setDestino(List<Ciudad> destino) {
        this.destino = destino;
    }

    public List<CuartoFrioCarguera> getFrios() {
        return frios;
    }

    public void setFrios(List<CuartoFrioCarguera> frios) {
        this.frios = frios;
    }

    public RegistroExportacion getFindStocksExportacion() {
        return findStocksExportacion;
    }

    public void setFindStocksExportacion(RegistroExportacion findStocksExportacion) {
        this.findStocksExportacion = findStocksExportacion;
    }

    public RegistroDetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(RegistroDetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public List<ItemVariedadVentaEmpaque> getVariedadesCaja() {
        return variedadesCaja;
    }

    public void setVariedadesCaja(List<ItemVariedadVentaEmpaque> variedadesCaja) {
        this.variedadesCaja = variedadesCaja;
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

}

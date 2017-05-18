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
import com.betel.flowers.model.ItemPrecio;
import com.betel.flowers.model.ItemVariedadVenta;
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
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.RegistroExportacionService;
import com.betel.flowers.service.TerminoExportacionService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.RegistroDetalleVenta;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<ItemVariedadVenta> detalleRegistroSock;
    private List<ItemVariedadVenta> selectDetalleRegistroSock;
    private RegistroDetalleVenta detalleVenta;

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
    private BodegaVirtualService bodegaService;

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
        this.detalleRegistroSock = new ArrayList<>();
        this.detalleVenta = new RegistroDetalleVenta();
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
            this.subClientes = new ArrayList<>();
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
        this.findStocksExportacion.setBodega(bodega);
        this.detalleRegistroSock = this.registroExportacionservice.obtenerListDisponibilidadFlag(this.findStocksExportacion, 1);
        if (this.detalleRegistroSock == null) {
            this.detalleRegistroSock = new ArrayList<>();
        }
    }

    public Double minPrecioLongitudVariedad(RegistroExportacion registro) {
        Double value = 00.01;
        if (registro.getVariedad().getGirasol()) {
            for (ItemPrecio precioVariedad : registro.getVariedad().getPrecios()) {
                if (precioVariedad.getGlongitud().equals(registro.getGlongitud())) {
                    value = precioVariedad.getMin();
                    break;
                }
            }
        } else {
            for (ItemPrecio precioVariedad : registro.getVariedad().getPrecios()) {
                if (precioVariedad.getLongitud().equals(registro.getLongitud())) {
                    value = precioVariedad.getMin();
                    break;
                }
            }
        }
        return value;
    }

    public Double maxPrecioLongitudVariedad(RegistroExportacion registro) {
        Double value = 01.00;
        if (registro.getVariedad().getGirasol()) {
            for (ItemPrecio precioVariedad : registro.getVariedad().getPrecios()) {
                if (precioVariedad.getGlongitud().equals(registro.getGlongitud())) {
                    value = precioVariedad.getMax();
                    break;
                }
            }
        } else {
            for (ItemPrecio precioVariedad : registro.getVariedad().getPrecios()) {
                if (precioVariedad.getLongitud().equals(registro.getLongitud())) {
                    value = precioVariedad.getMax();
                    break;
                }
            }
        }
        return value;
    }

    public void loadVariedad() {
        Variedad variedad = this.variedadService.findByCodigo(this.findStocksExportacion.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.findStocksExportacion.setVariedad(variedad);
        }
    }

    public void loadVariedadSelected() {
        Variedad variedad = this.variedadService.findByCodigo(this.findStocksExportacion.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.findStocksExportacion.setVariedad(variedad);
        }
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
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

    public List<ItemVariedadVenta> getDetalleRegistroSock() {
        return detalleRegistroSock;
    }

    public void setDetalleRegistroSock(List<ItemVariedadVenta> detalleRegistroSock) {
        this.detalleRegistroSock = detalleRegistroSock;
    }

    public List<ItemVariedadVenta> getSelectDetalleRegistroSock() {
        return selectDetalleRegistroSock;
    }

    public void setSelectDetalleRegistroSock(List<ItemVariedadVenta> selectDetalleRegistroSock) {
        this.selectDetalleRegistroSock = selectDetalleRegistroSock;
    }

    public RegistroDetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(RegistroDetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }
}

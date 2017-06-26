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
import com.betel.flowers.model.ItemPrecio;
import com.betel.flowers.model.ItemVariedadVenta;
import com.betel.flowers.model.ItemVariedadVentaEmpaque;
import com.betel.flowers.model.OrdenPreEmpaque;
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
import com.betel.flowers.web.bean.util.RegistroDetalleVenta;
import com.betel.flowers.web.util.FacesUtil;
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
    private List<ItemVariedadVentaEmpaque> variedadesCaja;
    private RegistroDetalleVenta detalleVenta;
    //CONTROL DE PRECIOS VARIEDAD LONGITUD
    private Double priceUnit = new Double(0);
    private Boolean AddflowersCaja;
    private Boolean AddPriceflowersTallo;
    private Double priceMax = new Double(0);
    private Double priceMin = new Double(0);
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
        this.detalleRegistroSock = new ArrayList<>();
        this.detalleVenta = new RegistroDetalleVenta();
        this.priceUnit = 0d;
        this.priceMin = 0d;
        this.priceMax = 100.00;
        this.AddflowersCaja = Boolean.TRUE;
        this.AddPriceflowersTallo = Boolean.FALSE;
        this.skip = Boolean.FALSE;
        this.variedadesCaja = new ArrayList<>();
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
        Especie especie = this.especieService.findByCodigo(this.findStocksExportacion.getVariedad().getEspecie());
        this.findStocksExportacion.setBodega(bodega);
        this.findStocksExportacion.getVariedad().setEspecie(especie);
        this.detalleRegistroSock = this.registroExportacionservice.obtenerListDisponibilidadFlag(this.findStocksExportacion, 1);
        if (this.detalleRegistroSock == null || this.detalleRegistroSock.isEmpty()) {
            this.detalleRegistroSock = new ArrayList<>();
            this.setAddPriceflowersTallo(Boolean.FALSE);
            this.priceUnit = 0d;
        } else {
            this.setAddPriceflowersTallo(Boolean.TRUE);
        }
        this.setAddflowersCaja(Boolean.TRUE);
    }

    public void loadVariedad() {
        Variedad variedad = this.variedadService.findByCodigo(this.findStocksExportacion.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.findStocksExportacion.setVariedad(variedad);
            loadPrcioUnitVariedadLongitud();
            this.priceUnit = 0d;
        }
    }

    public void loadPrcioUnitVariedadLongitud() {
        if (this.findStocksExportacion.getVariedad() != null) {
            if (this.findStocksExportacion.getVariedad().getGirasol()) {
                if (this.findStocksExportacion.getGlongitud() != null
                        && !this.findStocksExportacion.getGlongitud().equals("")) {
                    for (ItemPrecio gPrice : this.findStocksExportacion.getVariedad().getPrecios()) {
                        if (this.findStocksExportacion.getGlongitud().equals(gPrice.getGlongitud())) {
                            this.setPriceMin(gPrice.getMin());
                            this.setPriceMax(gPrice.getMax());
                            break;
                        }
                    }
                }
            } else {
                for (ItemPrecio lPrice : this.findStocksExportacion.getVariedad().getPrecios()) {
                    if (this.findStocksExportacion.getLongitud() == lPrice.getLongitud()) {
                        this.setPriceMin(lPrice.getMin());
                        this.setPriceMax(lPrice.getMax());
                        break;
                    }
                }
            }
        }
    }

    public void onClikListenerSelectList() {
        Boolean conValores = Boolean.FALSE;
        if (this.selectDetalleRegistroSock != null && !this.selectDetalleRegistroSock.isEmpty()) {
            Integer totalRamos = 0;
            ItemVariedadVentaEmpaque itemVentaSelectStock = new ItemVariedadVentaEmpaque();
            for (ItemVariedadVenta itemVariTotalRamos : this.selectDetalleRegistroSock) {
                if (itemVariTotalRamos.getNumeroRamos() != null) {
                    totalRamos = totalRamos + itemVariTotalRamos.getNumeroRamos();
                    itemVentaSelectStock.getRegistrosOrdenEmpaque().
                            add(new OrdenPreEmpaque(itemVariTotalRamos.getNumeroRamos(), itemVariTotalRamos.getNumeroTallosRamo(), itemVariTotalRamos.getRegistro()));
                    conValores = Boolean.TRUE;
                }
            }
            if (conValores) {
                itemVentaSelectStock.setVariedad(this.selectDetalleRegistroSock.get(0).getVariedad());
                itemVentaSelectStock.setNumeroRamos(totalRamos);
                itemVentaSelectStock.setPrecioUnit(this.getPriceUnit());
                itemVentaSelectStock.setPuntoCorte(this.selectDetalleRegistroSock.get(0).getPuntoCorte());
                if (this.selectDetalleRegistroSock.get(0).getVariedad().getGirasol()) {
                    itemVentaSelectStock.setGlongitud(this.selectDetalleRegistroSock.get(0).getGlongitud());
                } else {
                    itemVentaSelectStock.setLongitud(this.selectDetalleRegistroSock.get(0).getLongitud());
                }
                this.getVariedadesCaja().add(itemVentaSelectStock);
                this.detalleRegistroSock = new ArrayList<>();
                this.selectDetalleRegistroSock = new ArrayList<>();
            } else {
                FacesUtil.addMessageInfo("Por favor ingrese la cantidad de ramos.");
            }
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

    public void enableAgregarTallos() {
        if (this.priceUnit <= this.priceMax && this.priceUnit >= this.priceMin) {
            this.setAddflowersCaja(Boolean.FALSE);
        } else {
            this.setAddflowersCaja(Boolean.TRUE);
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

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Boolean getAddflowersCaja() {
        return AddflowersCaja;
    }

    public void setAddflowersCaja(Boolean AddflowersCaja) {
        this.AddflowersCaja = AddflowersCaja;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Boolean getAddPriceflowersTallo() {
        return AddPriceflowersTallo;
    }

    public void setAddPriceflowersTallo(Boolean AddPriceflowersTallo) {
        this.AddPriceflowersTallo = AddPriceflowersTallo;
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

}

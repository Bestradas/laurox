package com.laurox.lauroxonline.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.laurox.lauroxonline.domain.PedidoProveedor;
import com.laurox.lauroxonline.domain.PedidoProveedorDetalle;
import com.laurox.lauroxonline.repository.PedidoProveedorDetalleRepository;
import com.laurox.lauroxonline.repository.PedidoProveedorRepository;
import com.laurox.lauroxonline.web.rest.util.HeaderUtil;
import com.laurox.lauroxonline.web.rest.util.PaginationUtil;

/**
 * REST controller for managing PedidoProveedor.
 */
@RestController
@RequestMapping("/api")
public class PedidoProveedorResource {
	
	private final Logger log = LoggerFactory.getLogger(PedidoProveedorResource.class);

	@Inject
    private PedidoProveedorRepository pedidoProveedorRepository;
	
	@Inject
	private PedidoProveedorDetalleRepository pedidoProveedorDetalleRepository;
	
	
	/**
     * POST  /providerOrders -> Create a new pedido proveedor.
     */
    @RequestMapping(value = "/providerOrders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoProveedor> createPedidoProveedor(@Valid @RequestBody PedidoProveedor pedidoProveedor) throws URISyntaxException {
        log.debug("REST request to save PedidoProveedor : {}", pedidoProveedor);
        if (pedidoProveedor.getNmPedido() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pedidoProveedor cannot already have an ID").body(null);
        }
        List<PedidoProveedorDetalle> detalle=new ArrayList<PedidoProveedorDetalle>();
        detalle.addAll(pedidoProveedor.getDetallePedido());
        pedidoProveedor.setDetallePedido(new ArrayList<>());
        PedidoProveedor result = pedidoProveedorRepository.save(pedidoProveedor);
        setPedidoToDetail(detalle, pedidoProveedor);
        pedidoProveedorDetalleRepository.save(detalle);
        return ResponseEntity.created(new URI("/api/providerOrders/" + result.getNmPedido()))
                .headers(HeaderUtil.createEntityCreationAlert("providerOrder", result.getNmPedido().toString()))
                .body(result);
    }

    /**
     * PUT  /providerOrders -> Updates an existing proveedor.
     */
    @RequestMapping(value = "/providerOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoProveedor> updatePedidoProveedor(@Valid @RequestBody PedidoProveedor pedidoProveedor) throws URISyntaxException {
        log.debug("REST request to update Proveedor : {}", pedidoProveedor);
        if (pedidoProveedor.getNmProveedor() == null) {
            return createPedidoProveedor(pedidoProveedor);
        }
        PedidoProveedor result = pedidoProveedorRepository.save(pedidoProveedor);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("providerOrder", pedidoProveedor.getNmPedido().toString()))
                .body(result);
    }

    /**
     * GET  /providerOrders -> get all the proveedors.
     */
    @RequestMapping(value = "/providerOrders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PedidoProveedor>> getAllPedidoProveedor(Pageable pageable)
        throws URISyntaxException {
        Page<PedidoProveedor> page = pedidoProveedorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/providerOrders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /providerOrders/:nmPedido -> get the "nmPedido" PedidoProveedor.
     */
    @RequestMapping(value = "/providerOrders/{nmPedido}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoProveedor> getPedidoProveedor(@PathVariable Long nmPedido) {
        log.debug("REST request to get Proveedor : {}", nmPedido);
        PedidoProveedor pedidoProveedorObject=pedidoProveedorRepository.findOne(nmPedido);
        List<PedidoProveedorDetalle> detalle=pedidoProveedorDetalleRepository.findPedidoProveedorDetalle(nmPedido);
        pedidoProveedorObject.setDetallePedido(detalle);
        return Optional.ofNullable(pedidoProveedorObject)
            .map(pedidoProveedor -> new ResponseEntity<>(
            	pedidoProveedor,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /providerOrders/:nmPedido -> delete the "nmPedido" PedidoProveedor.
     */
    @RequestMapping(value = "/proveedors/{nmPedido}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long nmPedido) {
        log.debug("REST request to delete Proveedor : {}", nmPedido);
        pedidoProveedorRepository.delete(nmPedido);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("providerOrder", nmPedido.toString())).build();
    }
    
    
    
    public void setPedidoToDetail(List<PedidoProveedorDetalle> detalleList,PedidoProveedor pedidoProveedor){
    	for (PedidoProveedorDetalle pedidoProveedorDetalle : detalleList) {
			pedidoProveedorDetalle.setNmPedido(pedidoProveedor.getNmPedido());
		}
    }
    
}

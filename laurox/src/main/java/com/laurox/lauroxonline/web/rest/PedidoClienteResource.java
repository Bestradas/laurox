package com.laurox.lauroxonline.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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
import com.laurox.lauroxonline.domain.PedidoCliente;
import com.laurox.lauroxonline.domain.PedidoClienteDetalle;
import com.laurox.lauroxonline.domain.PedidoProveedor;
import com.laurox.lauroxonline.domain.PedidoProveedorDetalle;
import com.laurox.lauroxonline.repository.PedidoClienteDetalleRepository;
import com.laurox.lauroxonline.repository.PedidoClienteRepository;
import com.laurox.lauroxonline.web.rest.util.HeaderUtil;
import com.laurox.lauroxonline.web.rest.util.PaginationUtil;

/**
 * REST controller for managing PedidoCliente.
 */
@RestController
@RequestMapping("/api")
public class PedidoClienteResource {
	private final Logger log = LoggerFactory.getLogger(PedidoClienteResource.class);

	@Inject
    private PedidoClienteRepository pedidoClienteRepository;
	
	@Inject
	private PedidoClienteDetalleRepository pedidoClienteDetalleRepository;
	
	
	/**
     * POST  /customerOrders -> Create a new pedido cliente.
     */
    @RequestMapping(value = "/customerOrders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoCliente> createPedidoCliente(@Valid @RequestBody PedidoCliente pedidoCliente) throws URISyntaxException {
        log.debug("REST request to save PedidoCliente : {}", pedidoCliente);
        pedidoCliente.setFePedido(new Date());
        List<PedidoClienteDetalle> pedidoClienteDetalleList = new ArrayList<PedidoClienteDetalle>();
        pedidoClienteDetalleList.addAll(pedidoCliente.getDetallePedido());
        pedidoCliente.setDetallePedido(new ArrayList<PedidoClienteDetalle>());
        if (pedidoCliente.getNmPedido() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pedidoCliente cannot already have an ID").body(null);
        }
        PedidoCliente result = pedidoClienteRepository.save(pedidoCliente);
        setPedidoToDetail(pedidoClienteDetalleList, pedidoCliente);
        pedidoClienteDetalleRepository.save(pedidoClienteDetalleList);
        return ResponseEntity.created(new URI("/api/customerOrders/" + result.getNmPedido()))
                .headers(HeaderUtil.createEntityCreationAlert("customerOrders", result.getNmPedido().toString()))
                .body(result);
    }

    /**
     * PUT  /customerOrders -> Updates an existing pedido cliente.
     */
    @RequestMapping(value = "/customerOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoCliente> updatePedidoCliente(@Valid @RequestBody PedidoCliente pedidoCliente) throws URISyntaxException {
        log.debug("REST request to update Pedido Cliente : {}", pedidoCliente);
        if (pedidoCliente.getNmPedido() == null) {
            return createPedidoCliente(pedidoCliente);
        }
        PedidoCliente result = pedidoClienteRepository.save(pedidoCliente);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("customerOrders", pedidoCliente.getNmPedido().toString()))
                .body(result);
    }

    /**
     * GET  /providerOrders -> get all the proveedors.
     */
    @RequestMapping(value = "/customerOrders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PedidoCliente>> getAllPedidoCliente(Pageable pageable)
        throws URISyntaxException {
        Page<PedidoCliente> page = pedidoClienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customerOrders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customerOrders/:nmPedido -> get the "nmPedido" PedidoCliente.
     */
    @RequestMapping(value = "/customerOrders/{nmPedido}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PedidoCliente> getPedidoCliente(@PathVariable Long nmPedido) {
        log.debug("REST request to get Pedido Cliente : {}", nmPedido);
        PedidoCliente PedidoClienteObject=pedidoClienteRepository.findOne(nmPedido);
        List<PedidoClienteDetalle> detalle=pedidoClienteDetalleRepository.findPedidoClienteDetalle(nmPedido);
        PedidoClienteObject.setDetallePedido(detalle);
        return Optional.ofNullable(PedidoClienteObject)
            .map(PedidoCliente -> new ResponseEntity<>(
            	PedidoCliente,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /providerOrders/:nmPedido -> delete the "nmPedido" PedidoCliente.
     */
    @RequestMapping(value = "/customerOrders/approve/{nmPedidoApprove}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> approvePedidoCliente(@PathVariable Long nmPedidoApprove) {
        log.debug("REST request to approve PedidoProveedor : {}", nmPedidoApprove);
        	pedidoClienteRepository.approveRejectPedidoCliente(nmPedidoApprove,1);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityApproveAlert("customerOrders", nmPedidoApprove.toString())).build();
    }
    
    /**
     * DELETE  /providerOrders/:nmPedido -> delete the "nmPedido" PedidoCliente.
     */
    @RequestMapping(value = "/customerOrders/reject/{nmPedidoReject}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> rejectPedidoCliente(@PathVariable Long nmPedidoReject) {
        log.debug("REST request to approve PedidoProveedor : {}", nmPedidoReject);
        pedidoClienteRepository.approveRejectPedidoCliente(nmPedidoReject,2);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityRejectAlert("customerOrders", nmPedidoReject.toString())).build();
    }
    
    public void setPedidoToDetail(List<PedidoClienteDetalle> detalleList,PedidoCliente pedidoCliente){
    	for (PedidoClienteDetalle pedidoClienteDetalle : detalleList) {
    		pedidoClienteDetalle.setNmPedido(pedidoCliente.getNmPedido());
		}
    }
    
}

package com.laurox.lauroxonline.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.laurox.lauroxonline.domain.Producto;
import com.laurox.lauroxonline.repository.ProductoRepository;
import com.laurox.lauroxonline.web.rest.util.HeaderUtil;
import com.laurox.lauroxonline.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    @Inject
    private ProductoRepository productoRepository;

    /**
     * POST  /productos -> Create a new producto.
     */
    @RequestMapping(value = "/productos",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new producto cannot already have an ID").body(null);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + result.getIdproducto()))
                .headers(HeaderUtil.createEntityCreationAlert("producto", result.getIdproducto().toString()))
                .body(result);
    }

    /**
     * PUT  /productos -> Updates an existing producto.
     */
    @RequestMapping(value = "/productos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return createProducto(producto);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("producto", producto.getIdproducto().toString()))
                .body(result);
    }

    /**
     * GET  /productos -> get all the productos.
     */
    @RequestMapping(value = "/productos",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Producto>> getAllProductos(Pageable pageable)
        throws URISyntaxException {
        Page<Producto> page = productoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /productos -> get all the productos.
     */
    @RequestMapping(value = "/providerOrders/productsbyprov/{nmProveedor}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Producto>> getProductosByProv(@PathVariable String nmProveedor)
        throws URISyntaxException {
        return Optional.ofNullable(productoRepository.findPedidoProveedorProducts(nmProveedor))
                .map(producto -> new ResponseEntity<>(
                    producto,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    

    /**
     * GET  /productos/:idproducto -> get the "idproducto" producto.
     */
    @RequestMapping(value = "/productos/{idproducto}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Producto> getProducto(@PathVariable Long idproducto) {
        log.debug("REST request to get Producto : {}", idproducto);
        return Optional.ofNullable(productoRepository.findOne(idproducto))
            .map(producto -> new ResponseEntity<>(
                producto,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /productos/:idproducto -> delete the "idproducto" producto.
     */
    @RequestMapping(value = "/productos/{idproducto}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long idproducto) {
        log.debug("REST request to delete Producto : {}", idproducto);
        productoRepository.delete(idproducto);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("producto", idproducto.toString())).build();
    }
}

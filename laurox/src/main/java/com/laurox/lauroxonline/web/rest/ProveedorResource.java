package com.laurox.lauroxonline.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.laurox.lauroxonline.domain.Proveedor;
import com.laurox.lauroxonline.repository.ProveedorRepository;
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
 * REST controller for managing Proveedor.
 */
@RestController
@RequestMapping("/api")
public class ProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorResource.class);

    @Inject
    private ProveedorRepository proveedorRepository;

    /**
     * POST  /proveedors -> Create a new proveedor.
     */
    @RequestMapping(value = "/proveedors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proveedor> createProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to save Proveedor : {}", proveedor);
        if (proveedor.getNit() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new proveedor cannot already have an ID").body(null);
        }
        Proveedor result = proveedorRepository.save(proveedor);
        return ResponseEntity.created(new URI("/api/proveedors/" + result.getNit()))
                .headers(HeaderUtil.createEntityCreationAlert("proveedor", result.getNit().toString()))
                .body(result);
    }

    /**
     * PUT  /proveedors -> Updates an existing proveedor.
     */
    @RequestMapping(value = "/proveedors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proveedor> updateProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to update Proveedor : {}", proveedor);
        if (proveedor.getNit() == null) {
            return createProveedor(proveedor);
        }
        Proveedor result = proveedorRepository.save(proveedor);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("proveedor", proveedor.getNit().toString()))
                .body(result);
    }

    /**
     * GET  /proveedors -> get all the proveedors.
     */
    @RequestMapping(value = "/proveedors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Proveedor>> getAllProveedors(Pageable pageable)
        throws URISyntaxException {
        Page<Proveedor> page = proveedorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proveedors/:nit -> get the "nit" proveedor.
     */
    @RequestMapping(value = "/proveedors/{nit}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Proveedor> getProveedor(@PathVariable String nit) {
        log.debug("REST request to get Proveedor : {}", nit);
        return Optional.ofNullable(proveedorRepository.findOne(nit))
            .map(proveedor -> new ResponseEntity<>(
                proveedor,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proveedors/:nit -> delete the "nit" proveedor.
     */
    @RequestMapping(value = "/proveedors/{nit}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProveedor(@PathVariable String nit) {
        log.debug("REST request to delete Proveedor : {}", nit);
        proveedorRepository.delete(nit);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proveedor", nit.toString())).build();
    }
}

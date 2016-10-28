package com.laurox.lauroxonline.web.rest;

import com.laurox.lauroxonline.Application;
import com.laurox.lauroxonline.domain.Proveedor;
import com.laurox.lauroxonline.repository.ProveedorRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProveedorResource REST controller.
 *
 * @see ProveedorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProveedorResourceTest {

    private static final String DEFAULT_NIT = "SAMPLE_TEXT";
    private static final String UPDATED_NIT = "UPDATED_TEXT";
    private static final String DEFAULT_RAZON_SOCIAL = "SAMPLE_TEXT";
    private static final String UPDATED_RAZON_SOCIAL = "UPDATED_TEXT";
    private static final String DEFAULT_NOMBRE_CONTACTO = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE_CONTACTO = "UPDATED_TEXT";
    private static final String DEFAULT_TELEFONO = "SAMPLE_TEXT";
    private static final String UPDATED_TELEFONO = "UPDATED_TEXT";
    private static final String DEFAULT_CELULAR = "SAMPLE_TEXT";
    private static final String UPDATED_CELULAR = "UPDATED_TEXT";
    private static final String DEFAULT_DIRECCION = "SAMPLE_TEXT";
    private static final String UPDATED_DIRECCION = "UPDATED_TEXT";

    @Inject
    private ProveedorRepository proveedorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProveedorResource proveedorResource = new ProveedorResource();
        ReflectionTestUtils.setField(proveedorResource, "proveedorRepository", proveedorRepository);
        this.restProveedorMockMvc = MockMvcBuilders.standaloneSetup(proveedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proveedor = new Proveedor();
        proveedor.setNit(DEFAULT_NIT);
        proveedor.setRazonSocial(DEFAULT_RAZON_SOCIAL);
        proveedor.setNombreContacto(DEFAULT_NOMBRE_CONTACTO);
        proveedor.setTelefono(DEFAULT_TELEFONO);
        proveedor.setCelular(DEFAULT_CELULAR);
        proveedor.setDireccion(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedors.get(proveedors.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(DEFAULT_NIT);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testProveedor.getNombreContacto()).isEqualTo(DEFAULT_NOMBRE_CONTACTO);
        assertThat(testProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedor.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testProveedor.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void checkNitIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setNit(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setRazonSocial(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setNombreContacto(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setTelefono(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setDireccion(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedors
        restProveedorMockMvc.perform(get("/api/proveedors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT.toString())))
                .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
                .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
                .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())));
    }

    @Test
    @Transactional
    public void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{nit}", proveedor.getNit()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nit").value(DEFAULT_NIT.toString()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.nombreContacto").value(DEFAULT_NOMBRE_CONTACTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

		int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        proveedor.setNit(UPDATED_NIT);
        proveedor.setRazonSocial(UPDATED_RAZON_SOCIAL);
        proveedor.setNombreContacto(UPDATED_NOMBRE_CONTACTO);
        proveedor.setTelefono(UPDATED_TELEFONO);
        proveedor.setCelular(UPDATED_CELULAR);
        proveedor.setDireccion(UPDATED_DIRECCION);
        

        restProveedorMockMvc.perform(put("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedors.get(proveedors.size() - 1);
        assertThat(testProveedor.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testProveedor.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testProveedor.getNombreContacto()).isEqualTo(UPDATED_NOMBRE_CONTACTO);
        assertThat(testProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedor.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testProveedor.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

		int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Get the proveedor
        restProveedorMockMvc.perform(delete("/api/proveedors/{nit}", proveedor.getNit())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeDelete - 1);
    }
}

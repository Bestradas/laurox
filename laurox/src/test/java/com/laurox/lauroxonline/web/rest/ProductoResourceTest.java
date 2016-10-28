package com.laurox.lauroxonline.web.rest;

import com.laurox.lauroxonline.Application;
import com.laurox.lauroxonline.domain.Producto;
import com.laurox.lauroxonline.domain.Proveedor;
import com.laurox.lauroxonline.repository.ProductoRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProductoResource REST controller.
 *
 * @see ProductoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductoResourceTest {


    private static final Long DEFAULT_IDPRODUCTO = 1L;
    private static final Long UPDATED_IDPRODUCTO = 2L;
    private static final String DEFAULT_NOMBRE_PRODUCTO = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE_PRODUCTO = "UPDATED_TEXT";

    private static final Proveedor DEFAULT_IDPROVEEDOR = new Proveedor();
    private static final Proveedor UPDATED_IDPROVEEDOR = new Proveedor();
    private static final String DEFAULT_DESCRIPCION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPCION = "UPDATED_TEXT";
    private static final String DEFAULT_NUMERO_LOTE = "SAMPLE_TEXT";
    private static final String UPDATED_NUMERO_LOTE = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_PRECIO_UNITARIO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRECIO_UNITARIO = new BigDecimal(2);

    @Inject
    private ProductoRepository productoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductoResource productoResource = new ProductoResource();
        ReflectionTestUtils.setField(productoResource, "productoRepository", productoRepository);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        producto = new Producto();
        producto.setIdproducto(DEFAULT_IDPRODUCTO);
        producto.setNombreProducto(DEFAULT_NOMBRE_PRODUCTO);
        producto.setIdproveedor(DEFAULT_IDPROVEEDOR);
        producto.setDescripcion(DEFAULT_DESCRIPCION);
        producto.setNumeroLote(DEFAULT_NUMERO_LOTE);
        producto.setPrecioUnitario(DEFAULT_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto

        restProductoMockMvc.perform(post("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productos.get(productos.size() - 1);
        assertThat(testProducto.getIdproducto()).isEqualTo(DEFAULT_IDPRODUCTO);
        assertThat(testProducto.getNombreProducto()).isEqualTo(DEFAULT_NOMBRE_PRODUCTO);
        assertThat(testProducto.getIdproveedor()).isEqualTo(DEFAULT_IDPROVEEDOR);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getNumeroLote()).isEqualTo(DEFAULT_NUMERO_LOTE);
        assertThat(testProducto.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    public void checkIdproductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setIdproducto(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isBadRequest());

        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setNombreProducto(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isBadRequest());

        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdproveedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setIdproveedor(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isBadRequest());

        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setPrecioUnitario(null);

        // Create the Producto, which fails.

        restProductoMockMvc.perform(post("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isBadRequest());

        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productos
        restProductoMockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
                .andExpect(jsonPath("$.[*].idproducto").value(hasItem(DEFAULT_IDPRODUCTO.intValue())))
                .andExpect(jsonPath("$.[*].nombreProducto").value(hasItem(DEFAULT_NOMBRE_PRODUCTO.toString())))
                .andExpect(jsonPath("$.[*].idproveedor").value(hasItem(DEFAULT_IDPROVEEDOR)))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].numeroLote").value(hasItem(DEFAULT_NUMERO_LOTE.toString())))
                .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO.intValue())));
    }

    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.idproducto").value(DEFAULT_IDPRODUCTO.intValue()))
            .andExpect(jsonPath("$.nombreProducto").value(DEFAULT_NOMBRE_PRODUCTO.toString()))
            .andExpect(jsonPath("$.idproveedor").value(DEFAULT_IDPROVEEDOR))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.numeroLote").value(DEFAULT_NUMERO_LOTE.toString()))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

		int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        producto.setIdproducto(UPDATED_IDPRODUCTO);
        producto.setNombreProducto(UPDATED_NOMBRE_PRODUCTO);
        producto.setIdproveedor(UPDATED_IDPROVEEDOR);
        producto.setDescripcion(UPDATED_DESCRIPCION);
        producto.setNumeroLote(UPDATED_NUMERO_LOTE);
        producto.setPrecioUnitario(UPDATED_PRECIO_UNITARIO);
        

        restProductoMockMvc.perform(put("/api/productos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(producto)))
                .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productos.get(productos.size() - 1);
        assertThat(testProducto.getIdproducto()).isEqualTo(UPDATED_IDPRODUCTO);
        assertThat(testProducto.getNombreProducto()).isEqualTo(UPDATED_NOMBRE_PRODUCTO);
        assertThat(testProducto.getIdproveedor()).isEqualTo(UPDATED_IDPROVEEDOR);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getNumeroLote()).isEqualTo(UPDATED_NUMERO_LOTE);
        assertThat(testProducto.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

		int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Get the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Producto> productos = productoRepository.findAll();
        assertThat(productos).hasSize(databaseSizeBeforeDelete - 1);
    }
}

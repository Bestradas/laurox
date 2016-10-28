package com.laurox.lauroxonline.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "TL_PRODUCTOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @NotNull        
    @Column(name = "nm_producto", nullable = false)
    private Long idproducto;

    @NotNull
    @Size(min = 5, max = 150)
    @Pattern(regexp = "(^[a-zA-Z0-9 ]*$)")        
    @Column(name = "ds_nombre_producto", length = 150, nullable = false)
    private String nombreProducto;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nm_proveedor", nullable = false)
    @NotNull        
    private Proveedor idproveedor;

    @Size(max = 300)
    @Pattern(regexp = "(^[a-zA-Z0-9 ]*$)")        
    @Column(name = "ds_descripcion", length = 300)
    private String descripcion;

    @Size(max = 150)
    @Pattern(regexp = "(^[a-zA-Z0-9 ]*$)")        
    @Column(name = "ds_lote", length = 150)
    private String numeroLote;
         
    @Column(name = "nm_peso", precision=10, scale=2, nullable = true)
    private BigDecimal peso;
    
    @Size(max = 150)   
    @Column(name = "ds_foto", length = 150)
    private String foto;

    @NotNull        
    @Column(name = "nm_precio_unitario", precision=10, scale=2, nullable = false)
    private BigDecimal precioUnitario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Proveedor getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Proveedor idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Producto producto = (Producto) o;

        if ( ! Objects.equals(idproducto, producto.idproducto)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idproducto);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", idproducto='" + idproducto + "'" +
                ", nombreProducto='" + nombreProducto + "'" +
                ", idproveedor='" + idproveedor + "'" +
                ", descripcion='" + descripcion + "'" +
                ", numeroLote='" + numeroLote + "'" +
                ", peso='" + peso + "'" +
                ", foto='" + foto + "'" +
                ", precioUnitario='" + precioUnitario + "'" +
                '}';
    }
}

package com.laurox.lauroxonline.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PedidoProveedorDetalle.
 */
@Entity
@Table(name = "TL_PEDIDO_PROV_DETALLE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PedidoProveedorDetalle implements Serializable {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	@Column(name = "nm_pedido_cliente", nullable = false)
	    private Long id;


	    @NotNull
	    @Column(name = "nm_pedido", nullable = false)
	    private Long nmPedido;
	    
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "nm_producto", nullable = false)
	    @NotNull        
	    private Producto nmProducto;
	    
	    @NotNull        
	    @Column(name = "nm_cantidad", nullable = false)
	    private Long nmCantidad;
	    
	    @NotNull        
	    @Column(name = "nm_precio_unitario", precision=10, scale=2, nullable = false)
	    private BigDecimal precioUnitario;
	    
	    @Transient
		private Double subTotal;
	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getNmPedido() {
			return nmPedido;
		}

		public void setNmPedido(Long nmPedido) {
			this.nmPedido = nmPedido;
		}

		public Producto getNmProducto() {
			return nmProducto;
		}

		public void setNmProducto(Producto nmProducto) {
			this.nmProducto = nmProducto;
		}

		public Long getNmCantidad() {
			return nmCantidad;
		}

		public void setNmCantidad(Long nmCantidad) {
			this.nmCantidad = nmCantidad;
		}

		public BigDecimal getPrecioUnitario() {
			return precioUnitario;
		}

		public void setPrecioUnitario(BigDecimal precioUnitario) {
			this.precioUnitario = precioUnitario;
		}
		
		

		public Double getSubTotal() {
			return subTotal;
		}

		public void setSubTotal(Double subTotal) {
			this.subTotal = subTotal;
		}

		@Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }

	        PedidoProveedorDetalle pedidoProveedorDetalle = (PedidoProveedorDetalle) o;

	        if ( ! Objects.equals(id, pedidoProveedorDetalle.id)) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(id);
	    }

	    @Override
	    public String toString() {
	        return "PedidoProveedorDetalle{" +
	                "id=" + id +
	                ", nmPedido='" + nmPedido + "'" +
	                ", nmProducto='" + nmProducto + "'" +
	                ", nmCantidad='" + nmCantidad + "'" +
	                ", precioUnitario='" + precioUnitario + "'" +
	                ", subTotal='" + subTotal + "'" +
	                '}';
	    }
	}


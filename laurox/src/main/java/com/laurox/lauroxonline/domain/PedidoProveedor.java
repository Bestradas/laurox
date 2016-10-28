package com.laurox.lauroxonline.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PedidoProveedor.
 */
@Entity
@Table(name = "TL_PEDIDO_PROVEEDOR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PedidoProveedor implements Serializable {

 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
 	@Column(name = "nm_pedido", nullable = false)
    private Long nmPedido;

 	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nm_proveedor", nullable = false)
    @NotNull        
    private Proveedor nmProveedor;
  
    @NotNull        
    @Column(name = "fe_fecha_pedido", nullable = false)
    private Date fePedido;
    
            
    @Column(name = "fe_fecha_entrega_compr", nullable = false)
    private Date feCompra;
    
            
    @Column(name = "fe_fecha_entrega_real", precision=10, scale=2, nullable = false)
    private Date feEntrega;
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="nm_pedido", referencedColumnName="nm_pedido")
    private List<PedidoProveedorDetalle> detallePedido;

    @NotNull        
    @Column(name = "nm_precio_total", precision=10, scale=2, nullable = false)
	private BigDecimal total;

	public Long getNmPedido() {
		return nmPedido;
	}

	public void setNmPedido(Long nmPedido) {
		this.nmPedido = nmPedido;
	}

	public Proveedor getNmProveedor() {
		return nmProveedor;
	}

	public void setNmProveedor(Proveedor nmProveedor) {
		this.nmProveedor = nmProveedor;
	}

	public Date getFePedido() {
		return fePedido;
	}

	public void setFePedido(Date fePedido) {
		this.fePedido = fePedido;
	}

	public Date getFeCompra() {
		return feCompra;
	}

	public void setFeCompra(Date feCompra) {
		this.feCompra = feCompra;
	}

	public Date getFeEntrega() {
		return feEntrega;
	}
	
	

	public List<PedidoProveedorDetalle> getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(List<PedidoProveedorDetalle> detallePedido) {
		this.detallePedido = detallePedido;
	}

	public void setFeEntrega(Date feEntrega) {
		this.feEntrega = feEntrega;
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoProveedor pedidoProveedor = (PedidoProveedor) o;

        if ( ! Objects.equals(nmPedido, pedidoProveedor.nmPedido)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nmPedido);
    }

    @Override
    public String toString() {
        return "PedidoProveedor{" +
                "nmPedido=" + nmPedido +
                ", nmProveedor='" + nmProveedor + "'" +
                ", fePedido='" + fePedido + "'" +
                ", feCompra='" + feCompra + "'" +
                ", feEntrega='" + feEntrega + "'" +
                ", total='" + total + "'" +
                '}';
    }
}


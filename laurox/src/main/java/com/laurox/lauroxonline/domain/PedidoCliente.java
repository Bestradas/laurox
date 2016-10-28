package com.laurox.lauroxonline.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PedidoCliente.
 */
@Entity
@Table(name = "TL_PEDIDO_CLIENTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PedidoCliente implements Serializable {

 	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
 	@Column(name = "nm_pedido", nullable = false)
    private Long nmPedido;

 	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nm_cliente", nullable = false)
    @NotNull        
    private User nmCliente;
  
    @Column(name = "fe_fecha_pedido", nullable = false)
    private Date fePedido;
    
            
    @Column(name = "fe_fecha_entrega", nullable = false)
    private Date feEntrega;
    
    @OneToMany
    @JoinColumn(name="nm_pedido", referencedColumnName="nm_pedido")
    private List<PedidoClienteDetalle> detallePedido;

    @NotNull        
    @Column(name = "nm_precio_total", precision=10, scale=2, nullable = false)
	private BigDecimal total;
    
    @NotNull        
    @Column(name = "status", precision=10, scale=0, nullable = false)
	private Integer status;

	public Long getNmPedido() {
		return nmPedido;
	}

	public void setNmPedido(Long nmPedido) {
		this.nmPedido = nmPedido;
	}

	public User getNmCliente() {
		return nmCliente;
	}

	public void setNmCliente(User nmCliente) {
		this.nmCliente = nmCliente;
	}

	public Date getFePedido() {
		return fePedido;
	}

	public void setFePedido(Date fePedido) {
		this.fePedido = fePedido;
	}

	public Date getFeEntrega() {
		return feEntrega;
	}
	
	public void setFeEntrega(Date feEntrega) {
		this.feEntrega = feEntrega;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<PedidoClienteDetalle> getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(List<PedidoClienteDetalle> detallePedido) {
		this.detallePedido = detallePedido;
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

        PedidoCliente pedidoCliente = (PedidoCliente) o;

        if ( ! Objects.equals(nmPedido, pedidoCliente.nmPedido)) return false;

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
                ", nmCliente='" + nmCliente + "'" +
                ", fePedido='" + fePedido + "'" +
                ", feEntrega='" + feEntrega + "'" +
                ", total='" + total + "'" +
                ", status='" + status + "'" +
                '}';
    }
}
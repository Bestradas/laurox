package com.laurox.lauroxonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laurox.lauroxonline.domain.PedidoProveedorDetalle;

/**
 * Spring Data JPA repository for the PedidoProveedorDetalle entity.
 */
public interface PedidoProveedorDetalleRepository extends JpaRepository<PedidoProveedorDetalle,Long> {
	
	@Query("select p from PedidoProveedorDetalle p where p.nmPedido=:pedido")
	List<PedidoProveedorDetalle> findPedidoProveedorDetalle(@Param("pedido")Long pedido);

}

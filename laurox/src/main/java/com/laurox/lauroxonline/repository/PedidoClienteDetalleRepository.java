package com.laurox.lauroxonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laurox.lauroxonline.domain.PedidoClienteDetalle;

/**
 * Spring Data JPA repository for the PedidoClienteDetalle entity.
 */
public interface PedidoClienteDetalleRepository extends JpaRepository<PedidoClienteDetalle,Long> {
	
	@Query("select p from PedidoClienteDetalle p where p.nmPedido=:pedido")
	List<PedidoClienteDetalle> findPedidoClienteDetalle(@Param("pedido")Long pedido);
	
}

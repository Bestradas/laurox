package com.laurox.lauroxonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.laurox.lauroxonline.domain.PedidoCliente;

/**
 * Spring Data JPA repository for the PedidoProveedor entity.
 */
public interface PedidoClienteRepository extends JpaRepository<PedidoCliente,Long>{
	
	@Modifying
	@Transactional
	@Query("UPDATE PedidoCliente p set p.status=:status,p.feEntrega=current_timestamp where p.nmPedido=:pedido")
	int approveRejectPedidoCliente(@Param("pedido")Long pedido,@Param("status")Integer status);
	
}
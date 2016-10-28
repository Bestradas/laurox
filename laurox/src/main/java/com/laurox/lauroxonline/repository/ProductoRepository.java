package com.laurox.lauroxonline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laurox.lauroxonline.domain.Producto;

/**
 * Spring Data JPA repository for the Producto entity.
 */
public interface ProductoRepository extends JpaRepository<Producto,Long> {

	@Query("select p from Producto p where p.idproveedor.nit=:idproveedor")
	List<Producto> findPedidoProveedorProducts(@Param("idproveedor")String idproveedor);

	
}

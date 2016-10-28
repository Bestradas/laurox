package com.laurox.lauroxonline.repository;

import com.laurox.lauroxonline.domain.Proveedor;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proveedor entity.
 */
public interface ProveedorRepository extends JpaRepository<Proveedor,String> {

}

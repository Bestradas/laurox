package com.laurox.lauroxonline.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "TL_PROVEEDORES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proveedor implements Serializable {
	
	@Id
    @NotNull
    @Size(min = 5, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")        
    @Column(name = "nm_proveedor", length = 20, nullable = false)
    private String nit;

    @NotNull
    @Size(min = 5, max = 150)
    @Pattern(regexp = "(^[a-zA-Z0-9 ]*$)")        
    @Column(name = "ds_razon_social", length = 150, nullable = false)
    private String razonSocial;

    @NotNull
    @Size(min = 5, max = 150)
    @Pattern(regexp = "(^[a-zA-Z0-9 ]*$)")        
    @Column(name = "ds_nombre_contacto", length = 150, nullable = false)
    private String nombreContacto;

    @NotNull
    @Size(min = 7, max = 20)
    @Pattern(regexp = "(^[0-9]*$)")        
    @Column(name = "nm_telefono", length = 20, nullable = false)
    private String telefono;
    
    @Column(name = "nm_celular")
    private String celular;

    @NotNull
    @Size(min = 5, max = 150)
    @Pattern(regexp = "(^[a-zA-Z0-9# ]*$)")        
    @Column(name = "ds_direccion", length = 150, nullable = false)
    private String direccion;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Proveedor proveedor = (Proveedor) o;

        if ( ! Objects.equals(nit, proveedor.nit)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nit);
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "nit=" + nit +
                ", razonSocial='" + razonSocial + "'" +
                ", nombreContacto='" + nombreContacto + "'" +
                ", telefono='" + telefono + "'" +
                ", celular='" + celular + "'" +
                ", direccion='" + direccion + "'" +
                '}';
    }
}

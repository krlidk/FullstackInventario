package com.ecomarket.inventario.Model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "almacen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Almacén de productos")

public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlmacen; 

    @Column (nullable = false)
    private String almacennombre;

    @Column (nullable = false)
    private String direccion;

    @ManyToMany
    @JoinTable(
        name = "almacen_producto", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "almacen_id"), // Llave foránea hacia Almacen
        inverseJoinColumns = @JoinColumn(name = "producto_id") // Llave foránea hacia Producto
    )
    private List<Producto> productos;

}
package com.ecomarket.inventario.Model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
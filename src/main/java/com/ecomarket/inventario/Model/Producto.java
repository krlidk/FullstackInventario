package com.ecomarket.inventario.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Productos de la tienda")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false)
    private String productoNombre;

    @Column(nullable = false)
    private Integer stock;

    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private List<Almacen> almacenes;
}

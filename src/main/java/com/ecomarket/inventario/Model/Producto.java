package com.ecomarket.inventario.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producto_id;

    @Column(nullable = false)
    private String productoNombre;

    @Column(nullable = false)
    private Integer stock;

    //@Column(nullable = false)
    //private double precio;

    //@Column(nullable = false)
    //private String categoria;

    @ManyToMany(mappedBy = "productos")
    private List<Almacen> almacenes;

}

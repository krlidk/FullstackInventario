package com.ecomarket.inventario.Repository;

import java.util.Optional;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    
}

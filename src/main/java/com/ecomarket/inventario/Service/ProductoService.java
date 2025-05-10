package com.ecomarket.inventario.Service;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Model.Producto;
import com.ecomarket.inventario.Repository.AlmacenRepository;
import com.ecomarket.inventario.Repository.ProductoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    public Producto agregarProducto(Integer idAlmacen, Producto nuevoProducto) {
        // Verificar si el almacén existe
        Almacen almacen = almacenRepository.findByIdAlmacen(idAlmacen);
        if (almacen != null) {
            // Guardar el nuevo producto en la base de datos
            Producto productoGuardado = productoRepository.save(nuevoProducto);
            // Asociar el producto al almacén
            almacen.getProductos().add(productoGuardado);
            // Guardar los cambios en el almacén
            almacenRepository.save(almacen);
            return productoGuardado;
        } else {
            throw new IllegalArgumentException("Almacén no encontrado.");
        }
    }



}

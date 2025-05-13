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

    public String obtenerNombreProductoPorId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        return producto != null ? producto.getProductoNombre() : null; // Devuelve null si no se encuentra el producto
    }

    public Producto obtenerProductoEnAlmacen(Integer idAlmacen, Integer idProducto) {
        // Buscar el almacén por su ID
        Almacen almacen = almacenRepository.findByIdAlmacen(idAlmacen);
        if (almacen == null) {
            throw new IllegalArgumentException("Almacén no encontrado.");
        }
    
        // Buscar el producto dentro de los productos del almacén
        return almacen.getProductos().stream()
            .filter(producto -> producto.getIdProducto().equals(idProducto))
            .findFirst()
            .orElse(null); // Retorna null si no se encuentra el producto
    }

    public Producto actualizarProductoEnAlmacen(Integer idAlmacen, Producto productoActualizado) {
        // Verificar si el almacén existe
        Almacen almacen = almacenRepository.findByIdAlmacen(idAlmacen);
        if (almacen == null) {
            throw new IllegalArgumentException("Almacén no encontrado.");
        }
    
        // Verificar si el producto pertenece al almacén
        boolean productoExiste = almacen.getProductos().stream()
            .anyMatch(producto -> producto.getIdProducto().equals(productoActualizado.getIdProducto()));
    
        if (!productoExiste) {
            throw new IllegalArgumentException("El producto no pertenece al almacén especificado.");
        }
    
        // Actualizar el producto en la base de datos
        return productoRepository.save(productoActualizado);
    }

    // Método para eliminar un producto de un almacén
    public void eliminarProductoDeAlmacen(Integer idAlmacen, Integer idProducto) {
        // Verificar si el almacén existe
        Almacen almacen = almacenRepository.findByIdAlmacen(idAlmacen);
        if (almacen == null) {
            throw new IllegalArgumentException("Almacén no encontrado.");
        }

        // Buscar el producto dentro de los productos del almacén
        Producto producto = obtenerProductoEnAlmacen(idAlmacen, idProducto);
        if (producto != null) {
            // Eliminar el producto de la lista de productos del almacén
            almacen.getProductos().remove(producto);
            // Guardar los cambios en el almacén
            almacenRepository.save(almacen);
        } else {
            throw new IllegalArgumentException("Producto no encontrado en el almacén.");
        }
    }

}

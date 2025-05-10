package com.ecomarket.inventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.inventario.Model.Producto;
import com.ecomarket.inventario.Service.ProductoService;

@RestController
@RequestMapping("/api/v1/almacen/producto") //http://localhost:8080/api/v1/almacen/producto
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/agregarProducto/{idAlmacen}")
    public ResponseEntity<Producto> agregarProducto(
            @PathVariable Integer idAlmacen,
            @RequestBody Producto nuevoProducto) {
        Producto producto = productoService.agregarProducto(idAlmacen, nuevoProducto);
        return ResponseEntity.ok(producto);
    }
}

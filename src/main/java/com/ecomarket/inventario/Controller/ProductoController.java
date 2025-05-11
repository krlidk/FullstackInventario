package com.ecomarket.inventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecomarket.inventario.Model.Producto;
import com.ecomarket.inventario.Service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/almacen/producto") //http://localhost:8080/api/v1/almacen/producto
@Tag(name = "Producto", description = "Operaciones relacionadas con los productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @Operation(
    summary = "Agregar producto a un almacén",
    description = "Agregar un producto existente a un almacén por su ID. "
)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto agregado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El producto no existe o el almacén no se encuentra"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregarProducto/{idAlmacen}")
    public ResponseEntity<Producto> agregarProducto(
        @PathVariable Integer idAlmacen,
    @RequestBody Producto nuevoProducto) {
    // Verificar si el ID del producto no es nulo
    if (nuevoProducto.getIdProducto() != null) {
        // Obtener el nombre del producto por su ID
        String nombreProducto = productoService.obtenerNombreProductoPorId(nuevoProducto.getIdProducto());
        if (nombreProducto == null) {
            // Lanzar excepción 404 si el producto no existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con el ID proporcionado");
        }
        nuevoProducto.setProductoNombre(nombreProducto); // Completar el nombre automáticamente
    }
    // Agregar el producto al almacén
    Producto producto = productoService.agregarProducto(idAlmacen, nuevoProducto);
    return ResponseEntity.ok(producto);
    }
}
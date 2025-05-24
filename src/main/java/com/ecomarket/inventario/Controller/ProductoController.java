package com.ecomarket.inventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecomarket.inventario.Model.Producto;
import com.ecomarket.inventario.Service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/almacen/producto") //http://localhost:8080/api/v1/almacen/producto
@Tag(name = "Producto", description = "Operaciones relacionadas con los productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @Operation(
    summary = "Agregar producto a un almacén.",
    description = "Agregar un producto existente a un almacén existente por su ID y si un almacén ya tiene ese producto, actualiza el stock sumando lo ingresado. "
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Producto agregado exitosamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": \"30\" }"
            )
        )),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud, el stock del producto no puede ser nulo y debe ser un número natural."),
        @ApiResponse(responseCode = "404", description = "El producto no existe o el almacén no se encuentra"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/agregarProducto/{idAlmacen}")
    public ResponseEntity<Producto> agregarProducto(
        @PathVariable Integer idAlmacen,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Cuerpo de la solicitud para agregar un producto",
        required = true,
        content = @Content(
                mediaType = "application/json",
                examples = {@ExampleObject(
                name = "Ejemplo de producto agregando el nombre",
                value = "{ \"idProducto\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": 50 }"
            ),
            @ExampleObject(
                name = "Ejemplo de producto sin agregar nombre",
                value = "{ \"idProducto\": 2, \"stock\": 50 }"
            )
            }
        )
    )
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

        // Verificar si el producto ya existe en el almacén
        Producto productoExistente = productoService.obtenerProductoEnAlmacen(idAlmacen, nuevoProducto.getIdProducto());
        if (productoExistente != null) {
            // Sumar el stock existente con el nuevo stock
            int nuevoStock = productoExistente.getStock() + nuevoProducto.getStock();
            productoExistente.setStock(nuevoStock);
            Producto productoActualizado = productoService.actualizarProductoEnAlmacen(idAlmacen,productoExistente);
            return ResponseEntity.ok(productoActualizado);
        }

        // Agregar el producto al almacén si no existe
        Producto producto = productoService.agregarProducto(idAlmacen, nuevoProducto);
        return ResponseEntity.ok(producto);
    }

    @Operation(
    summary = "Actualizar un pproducto de un almacén.",
    description = "Actualiza el stock de un producto existente en un almacén existente por su ID. Se requieren parámetros IDs de almacén y producto, así como el stock a actualizar."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Producto atualizado exitosamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": \"30\" }"
            )
        )),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud."),
        @ApiResponse(responseCode = "404", description = "El producto no existe o el almacén no se encuentra"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizarStock/{idAlmacen}/{idProducto}")
    public ResponseEntity<?> actualizarStockProductoEnAlmacen(
        @PathVariable Integer idAlmacen,
        @PathVariable Integer idProducto,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cuerpo de la solicitud para actualizar el stock de un producto",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{ \"stock\": 100 }"
                )
            )
        )
        @RequestBody Producto productoActualizado) {
            try {
                // Obtener el producto existente en el almacén
                Producto productoExistente = productoService.obtenerProductoEnAlmacen(idAlmacen, idProducto);
                if (productoExistente == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado en el almacén.");
                }

                // Actualizar solo el stock del producto
                productoExistente.setStock(productoActualizado.getStock());

                // Guardar los cambios usando el servicio
                Producto productoActualizadoEnBD = productoService.actualizarProductoEnAlmacen(idAlmacen, productoExistente);

                return ResponseEntity.ok(productoActualizadoEnBD);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @Operation(
    summary = "ELiminar un producto de un almacén.",
    description = "Elimina un producto existente de un almacén existente por su ID. Se requieren parámetros IDs de almacén y producto."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El producto no existe o el almacén no se encuentra"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminar/{idAlmacen}/{idProducto}")
    public ResponseEntity<String> eliminarProductoDeAlmacen(
        @PathVariable Integer idAlmacen,
        @PathVariable Integer idProducto) {
        try {
            // Llamar al servicio para eliminar el producto del almacén
            productoService.eliminarProductoDeAlmacen(idAlmacen, idProducto);
            return ResponseEntity.ok("Producto eliminado del almacén exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
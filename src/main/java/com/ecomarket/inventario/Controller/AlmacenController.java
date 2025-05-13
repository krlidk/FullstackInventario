package com.ecomarket.inventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Service.AlmacenService;
import com.ecomarket.inventario.Service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/almacen") //http://localhost:8080/api/v1/almacen
@Tag(name = "Almacen", description = "Operaciones relacionadas con los almacenes")

public class AlmacenController {
    @Autowired
    private AlmacenService almacenService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<String>> listarMetodosDisponibles() {
        List<String> metodos = List.of(
            "Swagger UI: http://localhost:8080/swagger-ui.html",
            "JSON OpenAPI: http://localhost:8080/v3/api-docs",
            "GET /api/v1/almacen/obtenerAlmacenes - Obtener todos los almacenes",
            "GET /api/v1/almacen/buscarPorId - Buscar un almacén por ID",
            "GET /api/v1/almacen/buscarPorNombre - Buscar almacenes por nombre",
            "GET /api/v1/almacen/buscarPorDireccion - Buscar almacenes por dirección",
            "POST /api/v1/almacen/agregarAlmacen - Agregar un nuevo almacén",
            "POST /api/v1/almacen/producto/agregarProducto - Agregar un producto a un almacén",
            "PUT /api/v1/almacen/actualizarAlmacen - Actualizar un almacén existente",
            "DELETE /api/v1/almacen/eliminarAlmacen - Eliminar un almacén por ID"
        );
    return ResponseEntity.ok(metodos);
}

    @Operation(
    summary = "Obtener almacenes",
    description = "Obtiene una lista de todos los almacenes disponibles"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Lista de almacenes obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "[{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central\", \"direccion\": \"Calle Principal 123\" }, { \"idAlmacen\": 2, \"almacennombre\": \"Almacén Secundario\", \"direccion\": \"Calle Secundaria 456\" }]"
            )
        )),
        @ApiResponse(responseCode = "204", description = "No se encontraron almacenes"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping ("/obtenerAlmacenes")
    public ResponseEntity<List<Almacen>> obtenerAlmacenes() {
        List<Almacen> listaAlmacenes = almacenService.obtenerAlmacenes();
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no hay almacenes
        }
        listaAlmacenes.sort((a1, a2) -> a1.getIdAlmacen().compareTo(a2.getIdAlmacen())); // Ordenar por ID
        return ResponseEntity.ok(listaAlmacenes);
    }

    @Operation(
    summary = "Buscar almacén por ID",
    description = "Busca un almacén por su ID. Se requiere el ID como parámetro en la URL"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Almacén encontrado",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central\", \"direccion\": \"Calle Principal 123\",\"productos\": [{ \"idProducto\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": 50 }, { \"idProducto\": 2, \"productoNombre\": \"Cepillo de bambú\", \"stock\": 50 }] }"
            )
        )),
        @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping ("/buscarPorId/{idAlmacen}")
    public ResponseEntity<Almacen> buscarPorId(@PathVariable Integer idAlmacen) {
        Almacen almacen = almacenService.buscarPorId(idAlmacen);
        if (almacen == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
            
        }
        return ResponseEntity.ok(almacen);
    }

    @Operation(
    summary = "Buscar almacén por nombre",
    description = "Busca almacenes por su nombre. Se requiere parte del nombre como parámetro en la URL"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Almacén actualizado correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central\", \"direccion\": \"Calle Principal 123\",\"productos\": [{ \"idProducto\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": 50 }, { \"idProducto\": 2, \"productoNombre\": \"Cepillo de bambú\", \"stock\": 50 }] }"
            )
        )
        ),
        @ApiResponse(responseCode = "204", description = "No se encontraron almacenes"),
        @ApiResponse(responseCode = "404", description = "Almacén no existente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping ("/buscarPorNombre/{almacennombre}")
    public ResponseEntity<List<Almacen>> buscarPorNombre(@PathVariable String almacennombre) {
        List<Almacen> listaAlmacenes = almacenService.buscarPorNombre(almacennombre);
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no existen almacenes    
        }
        return ResponseEntity.ok(listaAlmacenes);
    }

    @Operation(
    summary = "Buscar almacén por dirección",
    description = "Busca almacenes por su dirección. Se requiere parte de la dirección como parámetro en la URL"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Almacén actualizado correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central\", \"direccion\": \"Calle Principal 123\",\"productos\": [{ \"idProducto\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": 50 }, { \"idProducto\": 2, \"productoNombre\": \"Cepillo de bambú\", \"stock\": 50 }] }"
            )
        )),
        @ApiResponse(responseCode = "204", description = "No se encontraron almacenes"),
        @ApiResponse(responseCode = "404", description = "Almacén no existente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping ("/buscarPorDireccion/{direccion}")
    public ResponseEntity<List<Almacen>> buscarPorDireccion(@PathVariable String direccion) {
        List<Almacen> listaAlmacenes = almacenService.buscarPorDireccion(direccion);
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no existen almacenes
        }
        return ResponseEntity.ok(listaAlmacenes);
    }

    @Operation(
    summary = "Agregar almacén",
    description = "Agrega un nuevo almacén. Se requiere un objeto Almacen en el cuerpo de la solicitud"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Almacén agregado correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central\", \"direccion\": \"Calle Principal 123\" }"
            )
        )),
        @ApiResponse(responseCode = "400", description = "Información inválida, asegúrese de ingresar los datos necesarios."),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping ("/agregarAlmacen") 
    public ResponseEntity<Almacen> guardarAlmacen(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Cuerpo de la solicitud para agregar un nuevo almacén",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = {@ExampleObject(
                name = "Ejemplo de almacén sin productos",
                value = "{ \"almacennombre\": \"Almacén A\", \"direccion\": \"Calle A 123\", \"productos\": [] }"
            ),
            @ExampleObject(
                name = "Ejemplo de almacén con productos",
                value = "{ \"almacennombre\": \"Almacén B\", \"direccion\": \"Calle B 123\", \"productos\": [{ \"idProducto\": 1, \"productoNombre\": \"Bolsa reutilizable\", \"stock\": 50 }, { \"idProducto\": 2, \"productoNombre\": \"Cepillo de bambú\", \"stock\": 50 }] }"
            )
        }
        )
    )
        @RequestBody Almacen almacen) {
            if (almacen.getAlmacennombre() == null || almacen.getAlmacennombre().isEmpty()) {
                almacen.setAlmacennombre("Almacén sin nombre"); 
            }  // Si el nombre del producto está vacío, se puede asignar un valor por defecto o manejarlo de otra manera

            if (almacen.getDireccion() == null || almacen.getDireccion().isEmpty()) {
                return ResponseEntity.badRequest().build(); // Devuelve un 400 Bad Request si la dirección está vacía
            }
            almacen.getProductos().forEach(producto -> {
                if (producto.getIdProducto() != null) {
                    // Obtener el nombre del producto por su ID
                    String nombreProducto = productoService.obtenerNombreProductoPorId(producto.getIdProducto());
                    if (producto.getProductoNombre() != null) {
                        producto.setProductoNombre(nombreProducto); // Completar el nombre automáticamente
                    }
                }
            });
            Almacen nuevoAlmacen = almacenService.guardarAlmacen(almacen);
            return ResponseEntity.ok(nuevoAlmacen); // Devuelve el almacén creado con un 200 OK
    }

    @Operation(
    summary = "Actualizar almacén",
    description = "Actualiza un almacén existente por su ID, solo se pueden actualizar el nombre y la dirección."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
        description = "Almacén actualizado correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                value = "{ \"idAlmacen\": 1, \"almacennombre\": \"Almacén Central a\", \"direccion\": \"Calle Principal 1234\" }"
            )
        )),
        @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/actualizarAlmacen/{idAlmacen}")
    public ResponseEntity<Almacen> actualizarAlmacen(
        @PathVariable Integer idAlmacen, 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Cuerpo de la solicitud para actualizar un almacén",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = 
                @ExampleObject(
                    name = "Ejemplo",
                    value = "{ \"almacennombre\": \"Almacén Principal\", \"direccion\": \"Calle Nueva 123\"}"
                )
        )
    )
        @RequestBody Almacen almacen) {
            Almacen almacenExistente = almacenService.buscarPorId(idAlmacen);
            if (almacenExistente == null) {
                return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
            }
            // Mantener la lista de productos del almacén existente
            almacen.setProductos(almacenExistente.getProductos());

            almacen.setIdAlmacen(idAlmacen); // Asegurarse de que el ID del objeto a actualizar sea correcto
            Almacen almacenActualizado = almacenService.actualizarAlmacen(almacen);
            return ResponseEntity.ok(almacenActualizado);
    }


    @Operation(
    summary = "Eliminar almacén",
    description = "Elimina un almacén por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Almacén eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Almacén no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping ("/eliminarAlmacen/{idAlmacen}")
    public ResponseEntity<?> eliminarAlmacen(@PathVariable Integer idAlmacen) {
        Almacen almacen = almacenService.buscarPorId(idAlmacen);
        if (almacen == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
        }
        almacenService.eliminarAlmacen(idAlmacen);
        return ResponseEntity.ok().body("Eliminado exitosamente."); // Devuelve un 204 No Content después de eliminar
    } 
}
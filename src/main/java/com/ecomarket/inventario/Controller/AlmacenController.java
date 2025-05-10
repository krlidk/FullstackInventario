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

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/almacen") //http://localhost:8080/api/v1/almacen

public class AlmacenController {
    @Autowired
    private AlmacenService almacenService;

    @GetMapping
public ResponseEntity<List<String>> listarMetodosDisponibles() {
    List<String> metodos = List.of(
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

    @GetMapping ("/obtenerAlmacenes")
    public ResponseEntity<List<Almacen>> obtenerAlmacenes() {
        List<Almacen> listaAlmacenes = almacenService.obtenerAlmacenes();
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no hay almacenes
        }
        listaAlmacenes.sort((a1, a2) -> a1.getIdAlmacen().compareTo(a2.getIdAlmacen())); // Ordenar por ID
        return ResponseEntity.ok(listaAlmacenes);
    }


    @GetMapping ("/buscarPorId/{idAlmacen}")
    public ResponseEntity<Almacen> buscarPorId(@PathVariable Integer idAlmacen) {
        Almacen almacen = almacenService.buscarPorId(idAlmacen);
        if (almacen == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
            
        }
        return ResponseEntity.ok(almacen);
    }


    @GetMapping ("/buscarPorNombre/{almacennombre}")
    public ResponseEntity<List<Almacen>> buscarPorNombre(@PathVariable String almacennombre) {
        List<Almacen> listaAlmacenes = almacenService.buscarPorNombre(almacennombre);
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no se encuentra el almacén
        }
        return ResponseEntity.ok(listaAlmacenes);
    }

    @GetMapping ("/buscarPorDireccion/{direccion}")
    public ResponseEntity<List<Almacen>> buscarPorDireccion(@PathVariable String direccion) {
        List<Almacen> listaAlmacenes = almacenService.buscarPorDireccion(direccion);
        if (listaAlmacenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content si no se encuentra el almacén
        }
        return ResponseEntity.ok(listaAlmacenes);
    }

    @PostMapping ("/agregarAlmacen") 
    public ResponseEntity<Almacen> guardarAlmacen(@RequestBody Almacen almacen) {
        Almacen nuevoAlmacen = almacenService.guardarAlmacen(almacen);
        return ResponseEntity.ok(nuevoAlmacen);
    }

    @PutMapping("/actualizarAlmacen/{idAlmacen}")
    public ResponseEntity<Almacen> actualizarAlmacen(@PathVariable Integer idAlmacen, @RequestBody Almacen almacen) {
        Almacen almacenExistente = almacenService.buscarPorId(idAlmacen);
        if (almacenExistente == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
        }
        almacen.setIdAlmacen(idAlmacen); // Asegurarse de que el ID del objeto a actualizar sea correcto
        Almacen almacenActualizado = almacenService.actualizarAlmacen(almacen);
        return ResponseEntity.ok(almacenActualizado);
    }

    @DeleteMapping ("/eliminarAlmacen/{idAlmacen}")
    public ResponseEntity<Void> eliminarAlmacen(@PathVariable Integer idAlmacen) {
        Almacen almacen = almacenService.buscarPorId(idAlmacen);
        if (almacen == null) {
            return ResponseEntity.notFound().build(); // Devuelve un 404 Not Found si no se encuentra el almacén
        }
        almacenService.eliminarAlmacen(idAlmacen);
        return ResponseEntity.noContent().build(); // Devuelve un 204 No Content después de eliminar
    } 
}

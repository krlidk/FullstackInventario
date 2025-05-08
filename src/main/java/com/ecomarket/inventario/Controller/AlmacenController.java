package com.ecomarket.inventario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Service.AlmacenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/almacen")

public class AlmacenController {
    @Autowired
    private AlmacenService almacenService;

    @GetMapping
public ResponseEntity<List<String>> listarMetodosDisponibles() {
    List<String> metodos = List.of(
        "GET /api/v1/almacen/obtenerAlmacenes - Obtener todos los almacenes",
        "POST /api/v1/almacen/agregarAlmacen - Agregar un nuevo almacén"
    );
    return ResponseEntity.ok(metodos);
}

    @GetMapping ("/obtenerAlmacenes")
    public ResponseEntity<List<Almacen>> obtenerAlmacenes() {
        List<Almacen> listaAlmacenes = almacenService.obtenerAlmacenes();
        return ResponseEntity.ok(listaAlmacenes);
    }

    @PostMapping ("/agregarAlmacen") 
    public ResponseEntity<Almacen> guardarAlmacen(@RequestBody Almacen almacen) {
        Almacen nuevoAlmacen = almacenService.guardarAlmacen(almacen);
        return ResponseEntity.ok(nuevoAlmacen);
    }
}

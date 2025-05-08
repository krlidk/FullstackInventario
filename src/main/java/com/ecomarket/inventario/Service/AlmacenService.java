package com.ecomarket.inventario.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Repository.AlmacenRepository;
import java.util.List;

@Service
public class AlmacenService {
    @Autowired
    private AlmacenRepository almacenRepository;

    public Almacen guardarAlmacen(Almacen almacen) {
        return almacenRepository.save(almacen); 
    }

    public List<Almacen> obtenerAlmacenes() {
        return almacenRepository.findAll(); //da la lista de todos los almacenes
    }
}
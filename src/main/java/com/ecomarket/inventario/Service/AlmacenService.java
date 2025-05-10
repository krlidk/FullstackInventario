package com.ecomarket.inventario.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.inventario.Model.Almacen;
import com.ecomarket.inventario.Repository.AlmacenRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class AlmacenService {
    @Autowired
    private AlmacenRepository almacenRepository;

    public Almacen guardarAlmacen(Almacen almacen) {
        return almacenRepository.save(almacen); 
    }

    public List<Almacen> obtenerAlmacenes() {
        return almacenRepository.findAll(); //da la lista de todos los almacenes
    }

    //public Almacen buscarPorId(Integer idAlmacen){
    //    return almacenRepository.findByIdAlmacen(idAlmacen); //busca el almacen por id
    //}

    public Almacen buscarPorId(Integer idAlmacen) {
        Almacen almacen = almacenRepository.findByIdAlmacen(idAlmacen); // Busca el almacén por ID
        if (almacen != null) {
            // Ordenar los productos por idProducto
            almacen.getProductos().sort((p1, p2) -> p1.getIdProducto().compareTo(p2.getIdProducto()));
        }
        return almacen;
    }

    @Transactional
    public void eliminarAlmacen(Integer idAlmacen) {
        almacenRepository.deleteByIdAlmacen(idAlmacen); 
    } //elimina el almacen por id

    public List<Almacen> buscarPorNombre(String almacennombre) {
        return almacenRepository.findByalmacennombre(almacennombre); //busca el almacen por nombre
    }

    public List<Almacen> buscarPorDireccion(String direccion) {
        return almacenRepository.findByDireccion(direccion); //busca el almacen por direccion
    }

    public Almacen actualizarAlmacen(Almacen almacen) {
        return almacenRepository.save(almacen); //actualiza el almacen
    }

}
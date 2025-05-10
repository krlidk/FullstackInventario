package com.ecomarket.inventario.Repository;

import java.util.List;

import com.ecomarket.inventario.Model.Almacen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {
    
    Almacen findByIdAlmacen(Integer idAlmacen); // Encuentra un almacen por su ID

    void deleteByIdAlmacen(Integer idAlmacen); // Elimina un almacen por su ID

    @Query("SELECT a FROM Almacen a WHERE LOWER(a.almacennombre) LIKE LOWER(CONCAT('%',:almacennombre,'%'))")
    List<Almacen> findByalmacennombre(String almacennombre); // Método para encontrar almacenes por nombre

    @Query("SELECT a FROM Almacen a WHERE LOWER(a.direccion) LIKE LOWER(CONCAT('%',:direccion,'%'))") 
    List<Almacen> findByDireccion(String direccion); // Encuentra almacenes por dirección

    //@Query("INSERT blabla") metodo para agregar productos a un almacen 

    
}

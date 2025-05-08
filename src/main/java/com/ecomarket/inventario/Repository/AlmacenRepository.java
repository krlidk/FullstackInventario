package com.ecomarket.inventario.Repository;

import java.util.List;
import com.ecomarket.inventario.Model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AlmacenRepository extends JpaRepository<Almacen, Integer> {


    Almacen findById(int almacen_id); // Encuentra un almacen por su ID

    void deleteById(int almacen_id); // Elimina un almacen por su ID

    List<Almacen> findByalmacennombre(String almacennombre); // Método para encontrar almacenes por nombre

    @Query("SELECT a FROM Almacen a WHERE LOWER(a.direccion) LIKE LOWER(CONCAT('%',:direccion,'%'))") 
    List<Almacen> findByDireccion(String direccion); // Encuentra almacenes por dirección

}

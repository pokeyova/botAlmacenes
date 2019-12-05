package com.chatbot.repository;

import com.chatbot.model.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ISucursalRepo extends JpaRepository<Sucursales, Integer> {
    @Query(value="select s.id, s.nombre, s.descripcion, s.fecha_reg from Sucursales s where s.nombre = :nombre limit 1", nativeQuery = true)
    Sucursales getIdSucursalByName(@Param("nombre")String nombre);
}

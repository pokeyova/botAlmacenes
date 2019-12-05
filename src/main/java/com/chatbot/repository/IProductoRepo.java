package com.chatbot.repository;

import com.chatbot.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductoRepo extends JpaRepository<Productos, Integer> {
    @Query(value="select p.id, p.codigo, p.nombre, p.descripcion, p.fecha_reg from Productos p where p.codigo = :codigo limit 1", nativeQuery = true)
    Productos getProductoByCodigo(@Param("codigo")String codigo);
}

package com.chatbot.repository;

import com.chatbot.model.ProductoSucursal;
import com.chatbot.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoSucursalRepo extends JpaRepository<ProductoSucursal,Integer> {
    @Query(value="select id, producto_id, sucursal_id, stock, fecha_reg from producto_sucursal WHERE sucursal_id= :id", nativeQuery = true)
    List<ProductoSucursal> obtieneProductoSucursal(@Param("id")int id);

    @Query(value="select id, producto_id, sucursal_id, stock, fecha_reg from producto_sucursal WHERE sucursal_id= :sucursal_id AND producto_id = :producto_id", nativeQuery = true)
    ProductoSucursal ProductoSucursalInfo(@Param("sucursal_id")int sucursal_id, @Param("producto_id")int producto_id);
}

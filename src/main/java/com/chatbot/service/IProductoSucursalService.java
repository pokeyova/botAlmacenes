package com.chatbot.service;

import com.chatbot.model.ProductoSucursal;

import java.util.List;

public interface IProductoSucursalService {
    String listaProductoSucursal();
   String listaProductosPorSucursal(int sucursal_id);

    void actualizarStock(int sucursal_id,String producto_id, int cantidad);
}

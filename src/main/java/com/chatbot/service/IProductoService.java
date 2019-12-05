package com.chatbot.service;

import com.chatbot.model.Productos;

import java.util.List;

public interface IProductoService {

    //REGISTRO
    void registrarProducto(String codigo, String nombre, String descripcion);

    // LISTADO
    String listaProductos();

    // LISTAR UN PRODUCTO
    String muestraProducto(String id);

    // MODIFICACION
    void modificarProducto(String columna, String id, String dato);

    // ELIMINACION
    void eliminaProducto(String id);
}

package com.chatbot.service;

import com.chatbot.model.Empleados;

public interface IEmpleadoService {

    //REGISTRO
    Empleados registrarEmpleado(int sucursal_id, String nombre, String direccion, String celular, String tipo, String fecha_reg);

    // LISTADO
    String listaEmpleados();

    // LISTAR UN EMPLEADO
    String muestraEmpleado(int id);

    // MODIFICACION
    void modificarEmpleado(String campo, int id, String dato);

    // ELIMINACION
    void eliminaEmpleado(int id);
}

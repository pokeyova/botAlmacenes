package com.chatbot.service;

import com.chatbot.model.Sucursales;

import java.util.List;

public interface ISucursalService {

    List<Sucursales> listar();

    Sucursales listaSucursal(int id);

    int getIdSucursalByName(String nombre);
}

package com.chatbot.service;

import com.chatbot.model.Sucursales;
import com.chatbot.repository.ISucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalServiceImpl implements  ISucursalService{

    @Autowired
    private ISucursalRepo repo;

    @Override
    public List<Sucursales> listar() {
        return repo.findAll();
    }

    @Override
    public Sucursales listaSucursal(int id) {
        Optional<Sucursales> oSucursal = repo.findById(id);
        Sucursales sucursal = oSucursal.get();
        return sucursal;
    }

    @Override
    public int getIdSucursalByName(String nombre) {
        Sucursales sucursal = repo.getIdSucursalByName(nombre);
        System.out.println(sucursal.getId() + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return sucursal.getId();
        //return 1;
    }

}


package com.chatbot.service;

import com.chatbot.model.ProductoSucursal;
import com.chatbot.model.Productos;
import com.chatbot.repository.IProductoRepo;
import com.chatbot.repository.IProductoSucursalRepo;
import com.chatbot.repository.ISucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoSucursalService implements IProductoSucursalService{
    @Autowired
    private IProductoSucursalRepo repoPS;

    @Autowired
    private IProductoRepo repoProducto;
    @Autowired
    private ISucursalRepo repoSucursal;

    @Override
    public String listaProductoSucursal() {
        String lista = "LISTA DE PRODUCTOS:";
        lista +="\n====================";
        lista +="\nID | CÓDIGO | SUCURSAL | NOMBRE | STOCK";
        lista +="\n=================================\n";
        for(ProductoSucursal ps : repoPS.findAll())
        {
            lista += ps.getProducto_id()+" | "+repoProducto.findById(ps.getProducto_id()).get().getCodigo()+" | "+repoSucursal.findById(ps.getSucursal_id()).get().getNombre()+" | "+repoProducto.findById(ps.getProducto_id()).get().getNombre()+" | "+ps.getStock()+"\n";
        }
        return lista;
    }
    @Override
    public String listaProductosPorSucursal(int sucursal_id) {
        String lista = "";
        if(repoPS.obtieneProductoSucursal(sucursal_id).size() > 0)
        {
            lista = "LISTA DE PRODUCTOS - " + repoSucursal.findById(sucursal_id).get().getNombre();
            lista +="\n===================================================";
            lista +="\nID | CÓDIGO | NOMBRE | STOCK";
            lista +="\n=================================\n";
            for(ProductoSucursal ps : repoPS.obtieneProductoSucursal(sucursal_id))
            {
                lista += ps.getProducto_id()+" | "+repoProducto.findById(ps.getProducto_id()).get().getCodigo()+ " | "+repoProducto.findById(ps.getProducto_id()).get().getNombre()+" | "+ps.getStock()+"\n";
            }
        }
        return lista;
    }

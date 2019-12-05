package com.chatbot.service;

import com.chatbot.model.ProductoSucursal;
import com.chatbot.repository.IProductoRepo;
import com.chatbot.repository.IProductoSucursalRepo;
import com.chatbot.repository.ISucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

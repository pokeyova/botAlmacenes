package com.chatbot.service;

import com.chatbot.model.Empleados;
import com.chatbot.model.Productos;
import com.chatbot.model.Sucursales;
import com.chatbot.repository.IProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepo repoProducto;

    @Override
    public void registrarProducto(String codigo, String nombre, String descripcion) {
        //Obtener la fecha actual
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_actual = formato.format(fecha);

        Productos producto = new Productos();
        producto.setCodigo(codigo.toUpperCase());
        producto.setNombre(nombre.toUpperCase());
        producto.setDescripcion(descripcion.toUpperCase());
        producto.setFecha_reg(fecha_actual);
        repoProducto.save(producto);
    }

    @Override
    public String listaProductos() {
        String lista = "LISTA DE PRODUCTOS:";
        lista +="\n====================";

        lista +="\nID | CÓDIGO | NOMBRE | DESCRIPCIÓN";
        lista +="\n=================================\n";
        for(Productos producto : repoProducto.findAll())
        {
            String descripcion ="";
            if(producto.getDescripcion() != null && producto.getDescripcion() != "")
            {
                descripcion = producto.getDescripcion();
            }
            else{
                descripcion = "S/D";
            }
            lista += producto.getId()+" | "+producto.getCodigo()+" | "+producto.getNombre()+" | "+descripcion+"\n";
        }
        return lista;
    }

    @Override
    public String muestraProducto(String id) {
        Productos producto = null;
         producto = repoProducto.getProductoByCodigo(id);
        if(isNumeric(id))
        {
            producto = repoProducto.findById(Integer.parseInt(id)).get();
        }

        String lista = "DATOS DEL PRODUCTO:";
        lista +="\n====================\n";
        String descripcion ="";
        if(producto.getDescripcion() != null && producto.getDescripcion() != "")
        {
            descripcion = producto.getDescripcion();
        }
        else{
            descripcion = "S/D";
        }
        lista += "ID: "+producto.getId()+"\nCÓDIGO: "+producto.getCodigo()+"\nNOMBRE: "+producto.getNombre()+"\nDESCRIPCIÓN: "+descripcion;
        return lista;
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    @Override
    public void modificarProducto(String columna, String id, String dato) {
        Productos producto = null;
        producto = repoProducto.getProductoByCodigo(id);
        if(isNumeric(id))
        {
            producto = repoProducto.findById(Integer.parseInt(id)).get();
        }

        if(columna.equals("codigo"))
        {
            producto.setCodigo(dato.toUpperCase());
        }
        else if(columna.equals("nombre"))
        {
            producto.setNombre(dato.toUpperCase());
        }
        else if(columna.equals("descripcion"))
        {
            producto.setDescripcion(dato.toUpperCase());
        }
        repoProducto.save(producto);
    }

    @Override
    public void eliminaProducto(String id) {
        Productos producto = null;
        producto = repoProducto.getProductoByCodigo(id);
        if(isNumeric(id))
        {
            producto = repoProducto.findById(Integer.parseInt(id)).get();
        }
        repoProducto.delete(producto);
    }

}

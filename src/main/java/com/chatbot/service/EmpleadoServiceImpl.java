package com.chatbot.service;

import com.chatbot.model.Empleados;
import com.chatbot.model.Sucursales;
import com.chatbot.repository.IEmpleadoRepo;
import com.chatbot.repository.ISucursalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepo repoEmpleado;

    @Autowired
    private ISucursalRepo repoSucursal;

    @Override
    public Empleados registrarEmpleado(int sucursal_id, String nombre, String direccion, String celular, String tipo, String fecha_reg) {
        Empleados empleado = new Empleados();
        empleado.setSucursal_id(sucursal_id);
        empleado.setNombre(nombre.toUpperCase());
        empleado.setDireccion(direccion.toUpperCase());
        empleado.setCelular(celular.toUpperCase());
        empleado.setTipo(tipo);
        //Obtener la fecha actual
        Date fecha = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_actual = formato.format(fecha);
        empleado.setFecha_reg(fecha_actual);
        Empleados empleadoRegistrado = null;
        System.out.print("||||||||||" +empleado + "EMPLEADOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        empleadoRegistrado = repoEmpleado.save(empleado);
        return empleadoRegistrado;
    }

    @Override
    public String listaEmpleados() {
        String lista = "LISTA DE EMPLEADOS:";
        lista +="\n====================";

        lista +="\nID | SUCURSAL | NOMBRE | CELULAR";
        lista +="\n=================================\n";
        for(Empleados empleado : repoEmpleado.findAll())
        {
            lista += empleado.getId()+" | "+repoSucursal.findById(empleado.getSucursal_id()).get().getNombre()+" | "+empleado.getNombre()+" | "+empleado.getCelular()+"\n";
        }
        return lista;
    }

    @Override
    public String muestraEmpleado(int id) {
        Empleados empleado = repoEmpleado.findById(id).get();

        String lista = "DATOS DEL EMPLEADO:";
       lista +="\n====================\n";
       Sucursales sucursal = repoSucursal.findById(empleado.getSucursal_id()).get();

       lista += "ID: "+empleado.getId()+"\nNOMBRE: "+empleado.getNombre()+"\nDIRECCIÓN: "+empleado.getDireccion()+"\nCELULAR: "+empleado.getCelular()+"\nSUCURSAL: "+sucursal.getNombre();
        return lista;
    }

    public void modificarEmpleado(String columna, int id, String dato)
    {
        Empleados empleado = repoEmpleado.findById(id).get();
        System.out.println(empleado + "EMPLEADOOOOOOOOOOOOOOOOOOOOOOOOO");

        if(columna.equals("nombre"))
        {
            empleado.setNombre(dato.toUpperCase());
        }
        else if(columna.equals("direccion"))
        {
            empleado.setDireccion(dato.toUpperCase());
        }
        else if(columna.equals("celular"))
        {
            empleado.setCelular(dato.toUpperCase());
        }
        else if(columna.equals("sucursal"))
        {
            empleado.setSucursal_id(Integer.parseInt(dato));
        }
        repoEmpleado.save(empleado);
    }


    @Override
    public void eliminaEmpleado(int id) {
        Empleados empleado = repoEmpleado.findById(id).get();
        repoEmpleado.delete(empleado);
    }
}

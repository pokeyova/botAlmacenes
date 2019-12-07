package com.chatbot.bot;

import com.chatbot.model.Empleados;
import com.chatbot.model.Sucursales;
import com.chatbot.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainBot extends  TelegramLongPollingBot{

    public int id= 0;
    public String seccion = "";
    public String accion = "";
    public String campo = "";
    public String codigo = "";
    public String codigo_modificar = "";
    public String sucursal = "";
    public String nombre = "";
    public String stock = "";
    public String direccion = "";
    public String celular = "";
    public String tipo = "";
    public String descripcion = "";
    public boolean sw_mensajeMempleados = false;
    public boolean sw_mensajeMProductos = false;

    private IEmpleadoService serviceEmpleado;
    private IProductoService serviceProducto;
    private ISucursalService serviceSucursal;
    private IProductoSucursalService serviceProductoSucursal;

    @Autowired
    public MainBot(EmpleadoServiceImpl sEmpleado, SucursalServiceImpl sSucursal, ProductoServiceImpl sProducto, ProductoSucursalService sProductoSUcursal)
    {
        this.serviceEmpleado = sEmpleado;
        this.serviceSucursal = sSucursal;
        this.serviceProducto = sProducto;
        this.serviceProductoSucursal = sProductoSUcursal;
    }

    // FUNCION PARA OBTENER LOS NUEVOS MENSAJES ENVIADOS AL BOT
    @Override
    public void onUpdateReceived(Update update) {
        // OBTENER EL MENSAJE QUE ENVIA EL USUARIO
        String texto = update.getMessage().getText();

        // INICIAR EL MENÚ
        menuInicial(texto, update);

        // OBTENER EL NOMBRE DEL USUARIO QUE ENVIO EL MENSAJE
        // String nombre_usuario = update.getMessage().getFrom().getFirstName();
    }
    @Override
    public String getBotUsername() {
        return "Iventario_Bot";
    }

    @Override
    public String getBotToken() {
        return "950532405:AAHjQ8CsJoB8h3yO7rJaKSJAEEWnrZSkUI0";
    }
   /*public String getBotToken() {
        return "1011018114:AAF75PTT1qglCByMO0RpdeVmYQuunu95cbw";
    }*/
    /****************************
     * SECCIÓN MENÚ DE MENSAJES
     * ***************************/

    // Menu inicial
    public void menuInicial(String comando,Update update)
    {
        // RESPONDER AL USUARIO
        SendMessage mensaje = new SendMessage();
        // ARMAR EL MENSAJE
        String texto_mensaje = "";
        // OPCIONES
        ReplyKeyboardMarkup kb = new ReplyKeyboardMarkup();
        //Crear una lista que guarde las opciones
        List<KeyboardRow> keyboard = new ArrayList();

        // RESPUESTA
        if(comando.toLowerCase().equals("/almacen"))
        {
            seccion = "INICIO";
            accion = "";
            campo = "";
            /*keyboard.clear();
            row.clear();*/
            // AGREGAR LAS OPCIONES INICIO
            keyboard = inicio();
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. EMPLEADOS") || comando.toLowerCase().equals("/empleados")) {
            seccion = "EMPLEADOS";
            accion = "";
            campo = "";
            limpiar();
            // AGREGAR LAS OPCIONES ACCIONES
            keyboard = acciones();
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("2. PRODUCTOS") || comando.toLowerCase().equals("/productos")){
            seccion = "PRODUCTOS";
            accion = "";
            campo = "";
            limpiar();
            // AGREGAR LAS OPCIONES ACCIONES
            keyboard = acciones();
            KeyboardRow row = new KeyboardRow();
            row.add("6. PRODUCTOS SUCURSAL");
            keyboard.add(row);
            kb.setKeyboard(keyboard);
            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
            mensaje.setReplyMarkup(kb);
        }
        else if(comando.equals("1. REGISTRAR") || accion.equals("REGISTRAR"))
        {
        // AGREGAR LOS DATOS A LA LISTA
            if(seccion.equals("EMPLEADOS")) {
                /*******************************
                 *             EMPLEADOS
                 * ****************************/
                if(accion.equals(""))
                {
                    texto_mensaje = "DATOS QUE SE NECESITAN:\n1. NOMBRE*\n2. DIRECCIÓN*\n3. CELULAR*\n4. SUCURSAL*\n===============================\nDEBE IR ELIGIENDO LOS CAMPOS(LOS CAMPOS CON * SON OBLIGATORIOS)";
                }
                // ASIGNAR LA ACCION ACTUAL
                accion = "REGISTRAR";

                // ASIGNAR CAMPOS
                if(!comando.equals("1. NOMBRE") && !comando.equals("2. DIRECCIÓN") && !comando.equals("3. CELULAR") && !comando.equals("4. SUCURSAL")  && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                {
                    if(campo.equals("nombre"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(direccion != "" && celular != "" && sucursal != "")
                        {
                            texto_mensaje = "CORRECTO!";
                        }

                        nombre = comando;
                    }
                    else if(campo.equals("direccion"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && celular != "" && sucursal != "") {
                            texto_mensaje = "CORRECTO!";
                        }
                        direccion = comando;
                    }
                    else if(campo.equals("celular"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && direccion != "" && sucursal != ""){
                            texto_mensaje = "CORRECTO!";
                        }

                        celular = comando;
                    }
                    else if(campo.equals("sucursal"))
                    {
                        texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                        if(nombre != "" && direccion != "" && celular != ""){
                            texto_mensaje = "CORRECTO!";
                        }

                        sucursal = comando;
                    }
                } //FIN ASIGANAR CAMPOS

                // VALIDAR QUE OPCIONES SE MOSTRARAN EN EL KEYBOARD
                keyboard = datosEmpleados();

                // PEDIR DATOS
                if(comando.equals("1. NOMBRE"))
                {
                    texto_mensaje = "INGRESE EL NOMBRE DEL EMPLEADO:";
                    campo = "nombre";
                }
                else if(comando.equals("2. DIRECCIÓN"))
                {
                    texto_mensaje = "INGRESE LA DIRECCIÓN DEL EMPLEADO:";
                    campo = "direccion";
                }
                else if(comando.equals("3. CELULAR"))
                {
                    texto_mensaje = "INGRESE EL CELULAR DEL EMPLEADO:";
                    campo = "celular";
                }
                else if(comando.equals("4. SUCURSAL"))
                {
                    texto_mensaje = "SELECCIONE LA SUCURSAL:";
                    campo = "sucursal";
                    if(serviceSucursal.listar().size() > 0)
                    {
                        // OBTENER EL KEYBOARD DE SUCURSALES
                        keyboard = keyBoardsucursales(serviceSucursal.listar());
                    }
                    if(keyboard.size() == 0)
                    {
                        // SI NO TIENE ELEMENTOS MOSTRAR ERROR
                        seccion = "INICIO";
                        keyboard = inicio();
                        kb.setKeyboard(keyboard);
                        texto_mensaje = "NO SE ENCONTRARON SUCURSALES. INTENTE MAS TARDE POR FAVOR.";
                        mensaje.setReplyMarkup(kb);
                        limpiar();
                    }
                }
                //FIN PEDIR DATOS
                else if(seccion.equals("PRODUCTOS"))
                {
                    /*******************************
                     *             PRODUCTOS
                     * ****************************/
                    if(accion.equals(""))
                    {
                        texto_mensaje = "DATOS QUE SE NECESITAN:\n1. CÓDIGO*\n2. NOMBRE*\n3. DESCRIPCIÓN\n===============================\nDEBE IR ELIGIENDO LOS CAMPOS(LOS CAMPOS CON * SON OBLIGATORIOS)";
                    }
                    // ASIGNAR LA ACCION ACTUAL
                    accion = "REGISTRAR";

                    // ASIGNAR CAMPOS
                    if(!comando.equals("1. CÓDIGO") && !comando.equals("2. NOMBRE") && !comando.equals("3. DESCRIPCIÓN") && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                    {
                        if(campo.equals("codigo"))
                        {
                            texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                            if(nombre != "" && descripcion != "")
                            {
                                texto_mensaje = "CORRECTO!";
                            }
                            codigo = comando;
                        }
                        else if(campo.equals("nombre"))
                        {
                            texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                            if(codigo != "" && descripcion != "") {
                                texto_mensaje = "CORRECTO!";
                            }
                            nombre = comando;
                        }
                        else if(campo.equals("descripcion"))
                        {
                            texto_mensaje = "CORRECTO!\nSELECCIONE OTRO CAMPO POR FAVOR";
                            if(codigo != "" && nombre != ""){
                                texto_mensaje = "CORRECTO!";
                            }
                            descripcion = comando;
                        }
                    }


                    //FIN ASIGANAR CAMPOS

                    // VALIDAR QUE OPCIONES SE MOSTRARAN EN EL KEYBOARD
                    keyboard = datosProductos();

                    // PEDIR DATOS
                    if(comando.equals("1. CÓDIGO"))
                    {
                        texto_mensaje = "INGRESE EL CÓDIGO DEL PRODUCTO:";
                        campo = "codigo";
                    }
                    else if(comando.equals("2. NOMBRE"))
                    {
                        texto_mensaje = "INGRESE EL NOMBRE DEL PRODUCTO:";
                        campo = "nombre";
                    }
                    // FIN PEDIR DATOS OBLIGATORIOS

                    if(codigo != "" && nombre != "")
                    {
                        if(comando.equals("CANCELAR"))
                        {
                            seccion = "INICIO";
                            keyboard = inicio();
                            kb.setKeyboard(keyboard);
                            texto_mensaje = "SELECCIONE UNA DE LAS OPCIONES.";
                            mensaje.setReplyMarkup(kb);
                            limpiar();
                            System.out.println("CANCELAR REGISTRO");
                        }
                        else if(comando.equals("GUARDAR"))
                        {
                            serviceProducto.registrarProducto(codigo,nombre,descripcion);
                            texto_mensaje = "REGISTRO ÉXITOSO";
                            System.out.println("GUARDO");
                            seccion = "INICIO";
                            keyboard = inicio();
                            kb.setKeyboard(keyboard);
                            mensaje.setReplyMarkup(kb);
                            limpiar();
                            seccion = "";
                            accion = "";
                            campo = "";
                        }
                        else{
                            texto_mensaje = "DATOS\n=======\nCÓDIGO: "+codigo+"\nNOMBRE: "+nombre+"\nDESCRIPCIÓN: "+descripcion;
                            //OPCIONES GUARDAR Y CANCELAR
                            keyboard = opcionesGC();
                            if(descripcion == "")
                            {
                                KeyboardRow row = new KeyboardRow();
                                row.add("3. DESCRIPCIÓN");
                                keyboard.add(row);
                            }
                        }
                    }

                    // DATO OPCIONAL
                    if(comando.equals("3. DESCRIPCIÓN"))
                    {
                        texto_mensaje = "INGRESE LA DESCRIPCIÓN DEL PRODUCTO:";
                        campo = "descripcion";
                    }
                    //FIN PEDIR DATO OPCIONAL

                }

                // SETTEAR LAS FILAS DEL KEYBOARD
                kb.setKeyboard(keyboard);
                // AGREGAR LOS BOTONES AL KEYBOARD
                mensaje.setReplyMarkup(kb);
                accion = "REGISTRAR";
            }
            else if(comando.equals("2. MODIFICAR") || accion.equals("MODIFICAR") || accion.equals("MODIFICARP"))
            {
                if(accion == "")
                {
                    accion = "MODIFICAR";
                }

                if(seccion.equals("EMPLEADOS"))
                {
                    /**********************
                     *       EMPLEADOS
                     * *******************/
                    // ASIGNAR LA ACCION A ID PARA SOLICITAR QUE SE INGRESE
                    if(id == 0)
                    {
                        texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE DESEA MODIFICAR POR FAVOR";
                        accion = "ID";
                    }

                    // ASIGNAR CAMPOS
                    if(!comando.equals("1. NOMBRE") && !comando.equals("2. DIRECCIÓN") && !comando.equals("3. CELULAR") && !comando.equals("4. SUCURSAL")  && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                    {
                        int respuesta = 0;
                        if(campo.equals("nombre"))
                        {
                            serviceEmpleado.modificarEmpleado("nombre",id,comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            nombre = "";
                        }
                        else if(campo.equals("direccion"))
                        {
                            serviceEmpleado.modificarEmpleado("direccion",id,comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            direccion = "";
                        }
                        else if(campo.equals("celular"))
                        {
                            serviceEmpleado.modificarEmpleado("celular",id,comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            celular = "";
                        }
                        else if(campo.equals("sucursal"))
                        {
                            String sucursal_id = serviceSucursal.getIdSucursalByName(comando)+"";
                            serviceEmpleado.modificarEmpleado("sucursal_id",id,sucursal_id);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            sucursal = "";
                        }
                        keyboard = datosEmpleados();
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                    }
                    //FIN ASIGANAR CAMPOS

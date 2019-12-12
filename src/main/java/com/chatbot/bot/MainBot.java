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
    int v_sucursal_id = 0;
    int cantidad_productos = 0;
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
            row = new KeyboardRow();
            row.add("7. REGISTRAR SALIDA");
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
                if(nombre != "" && direccion != "" && celular != "" && sucursal != "")
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
                        int resp=0;
                        //OBTENER LA FECHA
                        Date fecha = new Date();
                        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        String fecha_actual = formato.format(fecha);
                        //OBTENER EL ID DE LA SUCURSAL
                        int sucursal_id = serviceSucursal.getIdSucursalByName(sucursal);
                        Empleados empleadoNuevo = null;
                        empleadoNuevo = serviceEmpleado.registrarEmpleado(sucursal_id,nombre,direccion,celular,"EMPLEADO",fecha_actual);
                        if(empleadoNuevo != null)
                        {
                            texto_mensaje = "REGISTRO ÉXITOSO";
                            System.out.println("GUARDO");
                        }
                        else{
                            texto_mensaje = "ALGO SALIÓ MAL. INTENTE NUEVAMENTE POR FAVOR";
                        }

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
                        texto_mensaje = "DATOS\n=======\nNOMBRE: "+nombre+"\nDIRECCIÓN: "+direccion+"\nCELULAR: "+celular+"\nSUCURSAL: "+sucursal;
                        //OPCIONES GUARDAR Y CANCELAR
                        keyboard = opcionesGC();
                    }
                }
            }
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

                if(seccion.equals("EMPLEADOS")) {
                    /**********************
                     *       EMPLEADOS
                     * *******************/
                    // ASIGNAR LA ACCION A ID PARA SOLICITAR QUE SE INGRESE
                    if (id == 0) {
                        texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE DESEA MODIFICAR POR FAVOR";
                        accion = "ID";
                    }

                    // ASIGNAR CAMPOS
                    if (!comando.equals("1. NOMBRE") && !comando.equals("2. DIRECCIÓN") && !comando.equals("3. CELULAR") && !comando.equals("4. SUCURSAL") && !comando.equals("GUARDAR") && !comando.equals("CANCELAR")) 
                    {
                        int respuesta = 0;
                        if (campo.equals("nombre")) 
                        {
                            serviceEmpleado.modificarEmpleado("nombre", id, comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            nombre = "";
                        } 
                        else if (campo.equals("direccion")) 
                        {
                            serviceEmpleado.modificarEmpleado("direccion", id, comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            direccion = "";
                        } 
                        else if (campo.equals("celular")) 
                        {
                            serviceEmpleado.modificarEmpleado("celular", id, comando);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            celular = "";
                        } 
                        else if (campo.equals("sucursal")) 
                        {
                            String sucursal_id = serviceSucursal.getIdSucursalByName(comando) + "";
                            serviceEmpleado.modificarEmpleado("sucursal_id", id, sucursal_id);
                            texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                            sucursal = "";
                        }
                        keyboard = datosEmpleados();
                        kb.setKeyboard(keyboard);
                        mensaje.setReplyMarkup(kb);
                    }
                
                    //FIN ASIGANAR CAMPOS

                    // PEDIR DATOS
                    if(comando.equals("1. NOMBRE"))
                    {
                        texto_mensaje = "INGRESE EL NUEVO NOMBRE DEL EMPLEADO:";
                        campo = "nombre";
                    }
                    else if(comando.equals("2. DIRECCIÓN"))
                    {
                        texto_mensaje = "INGRESE LA NUEVA DIRECCIÓN DEL EMPLEADO:";
                        campo = "direccion";
                    }
                    else if(comando.equals("3. CELULAR"))
                    {
                        texto_mensaje = "INGRESE EL NUEVO CELULAR DEL EMPLEADO:";
                        campo = "celular";
                    }
                    else if(comando.equals("4. SUCURSAL"))
                    {
                        texto_mensaje = "SELECCIONE LA NUEVA SUCURSAL:";
                        campo = "sucursal";
                        if(serviceSucursal.listar().size() > 0)
                        {
                            // OBTENER EL KEYBOARD DE SUCURSALES
                            keyboard = keyBoardsucursales(serviceSucursal.listar());
                            kb.setKeyboard(keyboard);
                            mensaje.setReplyMarkup(kb);
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
                        /**********************
                         *       PRODUCTOS
                         * *******************/
                        if(accion.equals("MODIFICARP"))
                        {
                            // ASIGNAR CAMPOS
                            if(!comando.equals("1. CÓDIGO") && !comando.equals("2. NOMBRE") && !comando.equals("3. DESCRIPCIÓN") && !comando.equals("GUARDAR")  && !comando.equals("CANCELAR"))
                            {
                                System.out.println(campo + " | CAMPO");

                                if(codigo_modificar == "")
                                {
                                    codigo_modificar = comando;
                                    System.out.println(codigo_modificar);
                                    texto_mensaje = serviceProducto.muestraProducto(codigo_modificar);
                                    if(texto_mensaje != "")
                                    {
                                        texto_mensaje += "\n=================================";
                                        texto_mensaje += "\nSELECIONE UN CAMPO PARA MODIFICAR";
                                    }
                                    else{
                                        codigo_modificar = "";
                                        texto_mensaje = "NO SE ENCONTRÓ NINGUN PRODUCTO CON ESE ID/CÓDIGO. INTENTE NUEVAMENTE.";
                                        accion = "MODIFICAR";
                                    }
                                }

                                int respuesta = 0;
                                if(campo.equals("codigo"))
                                {
                                    serviceProducto.modificarProducto("codigo",codigo_modificar,comando);
                                    texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                                    codigo = "";
                                }
                                else if(campo.equals("nombre"))
                                {
                                    serviceProducto.modificarProducto("nombre",codigo_modificar,comando);
                                    texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                                    nombre = "";
                                }
                                else if(campo.equals("descripcion"))
                                {
                                    serviceProducto.modificarProducto("descripcion",codigo_modificar,comando);
                                    texto_mensaje = "DATO MODIFICADO CON ÉXITO!";
                                    descripcion = "";
                                }
                                keyboard = datosProductos();
                                kb.setKeyboard(keyboard);
                                mensaje.setReplyMarkup(kb);
                            }
                            //FIN ASIGANAR CAMPOS
                            // PEDIR DATOS
                            if(comando.equals("1. CÓDIGO"))
                            {
                                texto_mensaje = "INGRESE EL NUEVO CÓDIGO DEL PRODUCTO:";
                                campo = "codigo";
                            }
                            else if(comando.equals("2. NOMBRE"))
                            {
                                texto_mensaje = "INGRESE EL NUEVO NOMBRE DEL PRODUCTO:";
                                campo = "nombre";
                            }
                            else if(comando.equals("3. DESCRIPCIÓN"))
                            {
                                texto_mensaje = "INGRESE LA NUEVA DESCRIPCIÓN DEL PRODUCTO:";
                                campo = "descripcion";
                            }
                            //FIN PEDIR DATOS
                        }
                        else{
                            texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE MODIFICAR";
                            accion = "MODIFICARP";
                        }
                    }
                }
            else if(comando.equals("4. ELIMINAR") || accion.equals("ELIMINAR"))
        {
            if(seccion == "EMPLEADOS"){
                if(accion.equals("ELIMINAR"))
                {
                    serviceEmpleado.eliminaEmpleado(Integer.parseInt(comando));
                    texto_mensaje = "EMPLEADO ELIMINADO CON ÉXITO!";
                }
                else{
                    texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE QUIERE ELIMINAR";
                    accion = "ELIMINAR";
                }
            }
            else{
                if(accion.equals("ELIMINAR"))
                {
                    serviceProducto.eliminaProducto(comando);
                    texto_mensaje = "PRODUCTO ELIMINADO CON ÉXITO!";
                }
                else{
                    texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE ELIMINAR";
                    accion = "ELIMINAR";
                }
            }
        }
        else if(comando.equals("5. MOSTRAR") || accion.equals("MOSTRAR"))
            {
                if(seccion == "EMPLEADOS"){
                    if(accion.equals("MOSTRAR"))
                    {
                        texto_mensaje = serviceEmpleado.muestraEmpleado(Integer.parseInt(comando));
                        if(texto_mensaje == "")
                        {
                            texto_mensaje = "NO SE ENCONTRÓ NINGUN EMPLEADO CON ESE ID. POR FAVOR INGRESE OTRO ID";
                        }
                        else{
                            accion = "";
                        }
                    }
                    else{
                        texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE QUIERE VER";
                        accion = "MOSTRAR";
                    }
                }
                else{
                    if(accion.equals("MOSTRAR"))
                    {
                       texto_mensaje = serviceProducto.muestraProducto(comando);
                       if(texto_mensaje == "")
                    {
                    texto_mensaje = "NO SE ENCONTRÓ NINGUN PRODUCTO CON ESE ID/CÓDIGO. POR FAVOR INGRESE OTRO ID/CÓDIGO";
                }
                else{
                    accion = "";
                }
            }
            else{
                texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE VER";
                accion = "MOSTRAR";
            }
        }
    }
    else if(comando.equals("6. LISTAR"))
    {
        if(seccion == "EMPLEADOS"){
            texto_mensaje = serviceEmpleado.listaEmpleados();
        }
        else{
            texto_mensaje = serviceProducto.listaProductos();
        }
    }
    else if(comando.equals("6. PRODUCTOS SUCURSAL"))
    {
        texto_mensaje = serviceProductoSucursal.listaProductoSucursal();
    }
    else if(comando.equals("7. REGISTRAR SALIDA") || accion.equals("REGISTRAR_SALIDA") || accion.equals("REGISTRAR_SALIDA_PRODUCTO") || accion.equals("REGISTRAR_SALIDA_STOCK"))
        {
            if(accion.equals("REGISTRAR_SALIDA"))
            {
                v_sucursal_id = serviceSucursal.getIdSucursalByName(comando);
                texto_mensaje = serviceProductoSucursal.listaProductosPorSucursal(v_sucursal_id);
                if(texto_mensaje == "")
                {
                    texto_mensaje = "NO SE ENCONTRÓ NINGUN PRODUCTO EN ESTA SUCURSAL INTENTE CON OTRA";
                    keyboard = keyBoardsucursales(serviceSucursal.listar());
                    accion = "REGISTRAR_SALIDA";
                }
                else{
                    texto_mensaje += "\n======================================";
                    texto_mensaje += "\nINGRESE EL ID/CÓDIGO DEL PRODUCTO";
                    accion = "REGISTRAR_SALIDA_PRODUCTO";
                }
            }
            else if(accion.equals("REGISTRAR_SALIDA_PRODUCTO"))
            {
                codigo = comando;
                texto_mensaje += "\nINGRESE LA CANTIDAD DE PRODUCTOS";
                accion = "REGISTRAR_SALIDA_STOCK";
            }
            else if(accion.equals("REGISTRAR_SALIDA_STOCK"))
            {
                cantidad_productos = Integer.parseInt(comando);
                serviceProductoSucursal.actualizarStock(v_sucursal_id, codigo, cantidad_productos);
                texto_mensaje += "\nREGISTRO EXITOSO";

                seccion = "INICIO";
                keyboard = inicio();
                kb.setKeyboard(keyboard);
                mensaje.setReplyMarkup(kb);
                limpiar();
                seccion = "";
                accion = "";
                campo = "";
                cantidad_productos =0;
                codigo = "";
                v_sucursal_id = 0;
            }
            else{
                texto_mensaje = "SELECCIONE LA SUCURSAL DONDE SE REGISTRARA LA SALIDA";
                if(serviceSucursal.listar().size() > 0)
                {
                    // OBTENER EL KEYBOARD DE SUCURSALES
                    keyboard = keyBoardsucursales(serviceSucursal.listar());
                    accion = "REGISTRAR_SALIDA";
                }
                else
                {
                    // SI NO TIENE ELEMENTOS MOSTRAR ERROR
                    seccion = "INICIO";
                    keyboard = inicio();
                    kb.setKeyboard(keyboard);
                    texto_mensaje = "NO SE ENCONTRARON SUCURSALES. INTENTE MAS TARDE POR FAVOR.";
                    mensaje.setReplyMarkup(kb);
                    limpiar();
                    accion = "";
                }
            }
            kb.setKeyboard(keyboard);
            mensaje.setReplyMarkup(kb);
        }

    else if(accion.equals("ID"))
    {
        if(!comando.equals("2. MODIFICAR"))
        {
            id = Integer.parseInt(comando);
            texto_mensaje = serviceEmpleado.muestraEmpleado(id);
            if(texto_mensaje != "")
            {
                texto_mensaje += "\n=======================================";
                texto_mensaje += "\nSELECCIONE EL DATO QUE DESEA MODIFICAR";
                texto_mensaje += "\n=======================================";
                keyboard = datosEmpleados();
                kb.setKeyboard(keyboard);
                mensaje.setReplyMarkup(kb);
                accion = "MODIFICAR";
            }
            else{
                texto_mensaje += "\nNO SE ENCONTRO NINGUN EMPLEADO CON ESE ID. POR FAVOR INTENTE CON OTRO ID";
            }
        }
        else{
            texto_mensaje = "INGRESE EL ID DEL EMPLEADO QUE DESEA MODIFICAR POR FAVOR";
        }
    }
    else if(comando.equals("/start"))
    {
        texto_mensaje = "BIENVENIDO "+ update.getMessage().getFrom().getFirstName().toUpperCase();
    }

        mensaje.setText(texto_mensaje);
        // PREPARAR EL MENSAJE CON EL CHATID DEL USUARIO
        mensaje.setChatId(update.getMessage().getChatId());
        // EJECUTAR EL MENSAJE
        try {
            execute(mensaje);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("FIN");
    }

    public void limpiar()
    {
        codigo = "";
        codigo_modificar = "";
        nombre = "";
        direccion = "";
        celular = "";
        tipo = "";
        descripcion = "";
        id = 0;
        stock = "";
        sucursal = "";
    }
            /**************
             * FIN SECCIÓN
             * *************/

else{
            if(accion.equals("ELIMINAR"))
            {
                serviceProducto.eliminaProducto(comando);
                texto_mensaje = "PRODUCTO ELIMINADO CON ÉXITO!";
            }
            else{
                texto_mensaje = "INGRESE EL ID/CÓDIGO DEL PRODUCTO QUE QUIERE ELIMINAR";
                accion = "ELIMINAR";
            }
        }
        }

            public List<KeyboardRow> opcionesGC()
        {
            List<KeyboardRow> keyboard = new ArrayList();
            KeyboardRow row = new KeyboardRow();
            row.add("GUARDAR");//opcion1
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("CANCELAR");//opcion1
            keyboard.add(row);
            return keyboard;
        }

        public List<KeyboardRow> datosEmpleados()
        {
            List<KeyboardRow> keyboard = new ArrayList();
            KeyboardRow row = new KeyboardRow();
            System.out.println(nombre);
            System.out.println(direccion);
            System.out.println(celular);
            System.out.println(sucursal);

            if(nombre == "")
            {
                row.add("1. NOMBRE");//opcion1
                keyboard.add(row);
            }
            if(direccion == "")
            {
                row = new KeyboardRow();
                row.add("2. DIRECCIÓN");//Opcion2
                keyboard.add(row);
            }
            if(celular == "")
            {
                row = new KeyboardRow();
                row.add("3. CELULAR");//Opcion3
                keyboard.add(row);
            }
            if(sucursal == "")
            {
                row = new KeyboardRow();
                row.add("4. SUCURSAL");//Opcion3
                keyboard.add(row);
            }
            return keyboard;
        }

        public List<KeyboardRow> datosProductos()
        {
            List<KeyboardRow> keyboard = new ArrayList();
            KeyboardRow row = new KeyboardRow();
            if(codigo == "")
            {
                row.add("1. CÓDIGO");//opcion1
                keyboard.add(row);
            }
            if(nombre == "")
            {
                row = new KeyboardRow();
                row.add("2. NOMBRE");//Opcion2
                keyboard.add(row);
            }
            if(descripcion == "")
            {
                row = new KeyboardRow();
                row.add("3. DESCRIPCIÓN");//Opcion3
                keyboard.add(row);
            }
            return keyboard;
        }

        public List<KeyboardRow> acciones()
        {
            List<KeyboardRow> keyboard = new ArrayList();
            KeyboardRow row = new KeyboardRow();
            // AGREGAR LOS DATOS A LA LISTA
            row.add("1. REGISTRAR");//opcion1
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("2. MODIFICAR");//Opcion2
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("3. ELIMINAR");//Opcion3
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("4. MOSTRAR");//Opcion4
            keyboard.add(row);
            row = new KeyboardRow();
            row.add("5. LISTAR");//Opcion5
            keyboard.add(row);
            return keyboard;
        }

        /***********************
         * FUNCIONES PRODUCTOS
         * **********************/

    }


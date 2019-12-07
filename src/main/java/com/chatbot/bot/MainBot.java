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
                }


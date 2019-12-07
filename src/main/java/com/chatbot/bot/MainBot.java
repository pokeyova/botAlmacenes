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

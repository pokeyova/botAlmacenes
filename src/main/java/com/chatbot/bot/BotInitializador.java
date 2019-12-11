package com.chatbot.bot;

import com.chatbot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class BotInitializador {

    @Autowired
    public EmpleadoServiceImpl serviceEmpleadoImpl = new EmpleadoServiceImpl();

    @Autowired
    public SucursalServiceImpl serviceSucursal = new SucursalServiceImpl();
    @Autowired
    public ProductoServiceImpl serviceProducto = new ProductoServiceImpl();
    @Autowired
    public ProductoSucursalService servicesProductoSucursal = new ProductoSucursalService();

    public BotInitializador()
    {
    }

    // INICIA EL BOT
    @PostConstruct
    public void iniciarBot()
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MainBot(serviceEmpleadoImpl, serviceSucursal,serviceProducto,servicesProductoSucursal));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

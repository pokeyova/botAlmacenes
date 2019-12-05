package com.chatbot.controller;

import com.chatbot.repository.IEmpleadoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class InicioController {

    @Autowired
    private IEmpleadoRepo repoEmpleado;

    @GetMapping("/inicio")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "inicio";
    }

    @GetMapping("/lista")
    public String greeting(Model model) {
        model.addAttribute("empleados", repoEmpleado.findAll());
        return "inicio";
    }
}

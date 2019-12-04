package com.chatbot.repository;

import com.chatbot.model.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpleadoRepo extends JpaRepository<Empleados, Integer> {

}

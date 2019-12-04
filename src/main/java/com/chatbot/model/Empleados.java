package com.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name="empleados")
public class Empleados {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name = "sucursal_id", length = 10)
    private int sucursal_id;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "celular", length = 155)
    private String celular;

    @Column(name="tipo", length = 155)
    private  String tipo;

    @Column(name="fecha_reg", length = 155)
    private String fecha_reg;

    public String getFecha_reg() {
        return fecha_reg;
    }

    public void setFecha_reg(String fecha_reg) {
        this.fecha_reg = fecha_reg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCelular() {
        return celular;
    }

    public String getTipo() {
        return tipo;
    }

}

package com.chatbot.model;

import javax.persistence.*;

@Entity
@Table(name="producto_sucursal")
public class ProductoSucursal {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name = "producto_id", length = 10)
    private int producto_id;
    @Column(name = "sucursal_id", length = 10)
    private int sucursal_id;
    @Column(name = "stock", length = 11)
    private int stock;
    @Column(name = "fecha_reg", length = 155)
    private String fecha_reg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getFecha_reg() {
        return fecha_reg;
    }

    public void setFecha_reg(String fecha_reg) {
        this.fecha_reg = fecha_reg;
    }

    public void getCodigo() {
    }

    public void getNombre() {
    }
}

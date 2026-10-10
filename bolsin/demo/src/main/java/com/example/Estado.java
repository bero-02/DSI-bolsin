package com.example;

public class Estado {
    private String nombre;
    private String descripcion;

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
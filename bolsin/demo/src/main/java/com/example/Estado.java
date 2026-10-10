package com.example;

public class Estado {
    private String nombre;
    private String descripcion;

    public Estado(String nombre) {
        this.nombre = nombre;
    }
    // Comportamiento según secuencia
    public boolean sosEnviado() {
        return this.nombre.equalsIgnoreCase("Enviado"); 
    }

    //metodos setters y getter

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
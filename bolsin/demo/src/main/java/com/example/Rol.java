package com.example;

public class Rol {
    private String nombre;
    private String descripcion;

    public Rol(String nombre) {
        this.nombre = nombre;
    }
// Comportamiento según secuencia[cite: 3]
    public boolean esGCM() {
        return this.nombre.equalsIgnoreCase("GCM"); 
    }
    
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
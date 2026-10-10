package com.example;

public class ComisionMedica {
    private int codigo;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    public ComisionMedica(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
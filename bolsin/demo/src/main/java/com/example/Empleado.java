package com.example;

public class Empleado {
    private String nombre;
    private String apellido;
    private String email;
    private ComisionMedica comisionMedica;
    private Rol rol;
    private Usuario usuario;

    public Empleado(String nombre, String apellido, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public ComisionMedica getComisionMedica() { return comisionMedica; }
    public Rol getRol() { return rol; }
    public Usuario getUsuario() { return usuario; }

    public void setEmail(String email) { this.email = email; }
    public void setComisionMedica(ComisionMedica comisionMedica) { this.comisionMedica = comisionMedica; }
    public void setRol(Rol rol) { this.rol = rol; }
}
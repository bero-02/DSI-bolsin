package com.example;

public class Empleado {
    // Atributos según diagrama de clases
    private String nombre;
    private String apellido;
    private String email;
    private ComisionMedica comisionMedica;
    private Rol rol;
    private Usuario usuario;

    public Empleado(String nombre, String apellido, String email, Usuario usuario, Rol rol, ComisionMedica comisionMedica) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuario = usuario;
        this.rol = rol;
        this.comisionMedica = comisionMedica;
    }

    // --- Comportamiento según el diagrama de secuencia ---

    // Valida si el usuario pasado por parámetro es el usuario asociado a este empleado
    public boolean esTuUsuario(Usuario u) {
        return this.usuario.getNombre().equals(u.getNombre()); 
    }

    // Delega al Rol la responsabilidad de saber si es Gestor de Comisiones Médicas (GCM)[cite: 3]
    public boolean sosGCM() {
        return this.rol.esGCM(); 
    }

    // Devuelve el email para notificaciones[cite: 3]
    public String getDireccionEmail() {
        return this.email; 
    }

    // Devuelve la comisión médica asociada al empleado, necesaria para buscar bolsines[cite: 3]
    public ComisionMedica getComisionMedica() {
        return this.comisionMedica;
    }

    // --- Getters adicionales ---
    
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Rol getRol() { return rol; }
    public Usuario getUsuario() { return usuario; }
    
    // Si la organización lo requiere, se pueden agregar setters para actualizar información:
    public void setEmail(String email) { this.email = email; }
    public void setComisionMedica(ComisionMedica comisionMedica) { this.comisionMedica = comisionMedica; }
    public void setRol(Rol rol) { this.rol = rol; }
}
package ar.edu.dsi.gpstracker.model;

public class Empleado {
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

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA (CU 36)
    // ====================================================================

    // Mensaje: esTuUsuario()[cite: 18]
    // Delega la comparación al objeto Usuario para no romper el encapsulamiento.
    public boolean esTuUsuario(Usuario usuarioLogueado) {
        return this.usuario != null && 
               usuarioLogueado != null && 
               this.usuario.getNombre().equals(usuarioLogueado.getNombre());
    }

    // Mensaje: sosGCM()[cite: 18]
    // Delega la validación del rol al objeto Rol correspondiente.
    public boolean sosGCM() {
        return this.rol != null && this.rol.esGCM();
    }

    // Mensaje: getDireccionEmail()[cite: 18]
    // Retorna el email para que el Gestor pueda enviar la notificación.
    public String getDireccionEmail() {
        return this.email;
    }

    // Requerido implícitamente en el diagrama para luego pedirle el nombre a la CM[cite: 18]
    public ComisionMedica getComisionMedica() {
        return this.comisionMedica;
    }

    // ====================================================================
    // GETTERS BÁSICOS
    // ====================================================================

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Rol getRol() { return rol; }
    public Usuario getUsuario() { return usuario; }
}
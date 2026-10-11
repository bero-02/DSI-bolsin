package ar.edu.dsi.gpstracker.model;

public class Usuario {
    private String nombre;
    private String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA Y CASO DE USO 36
    // ====================================================================

    /**
     * Requerido implícitamente por el mensaje 3: esTuUsuario() del diagrama de secuencia[cite: 18].
     * El paso 2 del Caso de Uso 36 indica que el sistema "busca y muestra la CM del usuario logueado"[cite: 31].
     * Cuando el Gestor le pregunta al Empleado si le pertenece el usuario actual de la sesión,
     * el Empleado necesita obtener el "nombre" (identificador) de este Usuario para realizar la comparación.
     */
    public String getNombre() {
        return nombre;
    }

    // ====================================================================
    // GETTERS Y SETTERS (Encapsulamiento y Mutabilidad controlada)
    // ====================================================================

    public String getContrasena() {
        return contrasena;
    }

    /**
     * Permite la actualización de la contraseña, ya que es un atributo que por 
     * reglas de negocio y seguridad puede (y debe) cambiar a lo largo del tiempo.
     * 
     * Nota: No se expone un método setNombre() aplicando el patrón de Variaciones Protegidas, 
     * ya que el nombre actúa como la identidad del usuario en el sistema y no debe alterarse 
     * luego de su creación.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
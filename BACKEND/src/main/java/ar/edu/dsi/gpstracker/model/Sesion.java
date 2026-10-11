package ar.edu.dsi.gpstracker.model;

import java.time.LocalDateTime;

public class Sesion {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Usuario usuario;

    public Sesion(Usuario usuario, LocalDateTime fechaHoraInicio) {
        this.usuario = usuario;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA Y CASO DE USO 36
    // ====================================================================

    /**
     * Mensaje 2: getUsuarioLogeado()[cite: 18].
     * El paso 2 del Caso de Uso 36 establece que el Sistema "busca y muestra 
     * la CM del usuario logueado"[cite: 31]. 
     * Para lograr esto, el Gestor primero debe pedirle a la Sesion actual cuál es ese usuario.
     */
    public Usuario getUsuarioLogeado() {
        return this.usuario;
    }

    // ====================================================================
    // GETTERS Y SETTERS (Encapsulamiento y Mutabilidad controlada)
    // ====================================================================

    public LocalDateTime getFechaHoraInicio() { 
        return fechaHoraInicio; 
    }
    
    public LocalDateTime getFechaHoraFin() { 
        return fechaHoraFin; 
    }

    /**
     * Único setter expuesto.
     * La sesión nace con una fecha de inicio y un usuario inmutables. 
     * La fecha de fin solo se registra cuando el usuario cierra su sesión.
     */
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { 
        this.fechaHoraFin = fechaHoraFin; 
    }
}
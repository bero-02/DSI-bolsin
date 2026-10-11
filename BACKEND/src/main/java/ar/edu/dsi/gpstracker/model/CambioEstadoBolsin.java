package ar.edu.dsi.gpstracker.model;

import java.time.LocalDateTime;

public class CambioEstadoBolsin {
    private int codigo;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Estado estado;

    public CambioEstadoBolsin(int codigo, LocalDateTime fechaHoraInicio, Estado estado) {
        this.codigo = codigo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
    }

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA Y CASO DE USO 36
    // ====================================================================

    /**
     * Mensaje 7.1: sosActual()[cite: 18]
     * Determina si este cambio de estado es el vigente del Bolsín.
     * Según la lógica del negocio, es el actual si no se le ha registrado una fecha de fin.
     */
    public boolean sosActual() {
        return this.fechaHoraFin == null;
    }

    /**
     * Mensaje 7.2: sosEnviado()[cite: 18]
     * El paso 3 del Caso de Uso 36 requiere filtrar los bolsines que se encuentran 
     * específicamente en estado "Enviado"[cite: 31]. 
     * Aplicando el patrón Experto en Información, delega la validación al objeto Estado.
     */
    public boolean sosEnviado() {
        return this.estado != null && this.estado.sosEnviado();
    }

    // ====================================================================
    // GETTERS Y SETTERS (Encapsulamiento y Mutabilidad controlada)
    // ====================================================================

    public int getCodigo() { 
        return codigo; 
    }
    
    public LocalDateTime getFechaHoraInicio() { 
        return fechaHoraInicio; 
    }
    
    public LocalDateTime getFechaHoraFin() { 
        return fechaHoraFin; 
    }
    
    public Estado getEstado() { 
        return estado; 
    }

    /**
     * Único setter permitido según principios GRASP para proteger las variaciones.
     * Los demás atributos nacen con el objeto y no cambian, pero la fecha de fin 
     * se "setea" en el momento exacto en que el estado deja de ser el actual.
     */
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { 
        this.fechaHoraFin = fechaHoraFin; 
    }
}
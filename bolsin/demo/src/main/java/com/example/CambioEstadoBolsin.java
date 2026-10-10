package com.example;
import java.util.Date;

public class CambioEstadoBolsin {
    private int codigo;
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Estado estado;

    public CambioEstadoBolsin(int codigo, Date fechaHoraInicio, Estado estado) {
        this.codigo = codigo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
    }
    // Comportamiento según secuencia
    public boolean sosActual() {
        // Es el estado actual si no tiene fecha de fin[cite: 3]
        return this.fechaHoraFin == null;
    }

    public boolean sosEnviado() {
        return this.estado.sosEnviado(); //[cite: 3]
    }

    //metodos setters y getter
    public int getCodigo() { return codigo; }
    public Date getFechaHoraInicio() { return fechaHoraInicio; }
    public Date getFechaHoraFin() { return fechaHoraFin; }
    public Estado getEstado() { return estado; }

    public void setFechaHoraFin(Date fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
}
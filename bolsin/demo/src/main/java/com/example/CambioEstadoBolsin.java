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

    public int getCodigo() { return codigo; }
    public Date getFechaHoraInicio() { return fechaHoraInicio; }
    public Date getFechaHoraFin() { return fechaHoraFin; }
    public Estado getEstado() { return estado; }

    public void setFechaHoraFin(Date fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
}
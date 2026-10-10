package com.example;
import java.util.Date;

public class Sesion {
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Usuario usuario;

    public Sesion(Usuario usuario, Date fechaHoraInicio) {
        this.usuario = usuario;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraInicio() { return fechaHoraInicio; }
    public Date getFechaHoraFin() { return fechaHoraFin; }
    public Usuario getUsuario() { return usuario; }

    public void setFechaHoraFin(Date fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
}
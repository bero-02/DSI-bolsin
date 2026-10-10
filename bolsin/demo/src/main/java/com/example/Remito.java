package com.example;
import java.util.Date;

public class Remito {
    private Date fecha;
    private int numero;
    private Estado estado;

    public Remito(int numero, Date fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    public Date getFecha() { return fecha; }
    public int getNumero() { return numero; }
    public Estado getEstado() { return estado; }

    public void setEstado(Estado estado) { this.estado = estado; }
}
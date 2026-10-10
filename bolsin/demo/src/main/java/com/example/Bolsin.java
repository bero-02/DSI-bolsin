package com.example;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bolsin {
    private Date fecha;
    private int numeroBolsin;
    private int numeroPrecinto;
    private double peso;
    private List<CambioEstadoBolsin> cambioEstado;

    public Bolsin(int numeroBolsin, Date fecha) {
        this.numeroBolsin = numeroBolsin;
        this.fecha = fecha;
        this.cambioEstado = new ArrayList<>();
    }

    public Date getFecha() { return fecha; }
    public int getNumeroBolsin() { return numeroBolsin; }
    public int getNumeroPrecinto() { return numeroPrecinto; }
    public double getPeso() { return peso; }
    public List<CambioEstadoBolsin> getCambioEstado() { return cambioEstado; }

    public void setNumeroPrecinto(int numeroPrecinto) { this.numeroPrecinto = numeroPrecinto; }
    public void setPeso(double peso) { this.peso = peso; }
    public void addCambioEstado(CambioEstadoBolsin cambio) { this.cambioEstado.add(cambio); }
}
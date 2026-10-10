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
    private ComisionMedica comisionMedicaDestino;

    public Bolsin(int numeroBolsin, Date fecha) {
        this.numeroBolsin = numeroBolsin;
        this.fecha = fecha;
        this.cambioEstado = new ArrayList<>();
    }
    // Comportamiento según secuencia[cite: 3]
    public boolean esEnviado() {
        CambioEstadoBolsin actual = obtenerEstadoActual(); //[cite: 3]
        if (actual != null) {
            return actual.sosEnviado(); //[cite: 3]
        }
        return false;
    }

    public CambioEstadoBolsin obtenerEstadoActual() {
        for (CambioEstadoBolsin cambio : cambioEstado) {
            if (cambio.sosActual()) { //[cite: 3]
                return cambio;
            }
        }
        return null;
    }

        // Valida si la CM destino es igual a la del usuario
    public boolean esCMUsuarioIgualCMDestino(ComisionMedica cmUsuario) {
        return this.comisionMedicaDestino != null && 
            cmUsuario != null && 
            this.comisionMedicaDestino.getCodigo() == cmUsuario.getCodigo();
    }

    // Getters
    public int getNumeroDePrecinto() { return numeroPrecinto; } //[cite: 3]
    public void setComisionMedicaDestino(ComisionMedica cm) { this.comisionMedicaDestino = cm; }


    public Date getFecha() { return fecha; }
    public int getNumeroBolsin() { return numeroBolsin; }
    public int getNumeroPrecinto() { return numeroPrecinto; }
    public double getPeso() { return peso; }
    public List<CambioEstadoBolsin> getCambioEstado() { return cambioEstado; }

    public void setNumeroPrecinto(int numeroPrecinto) { this.numeroPrecinto = numeroPrecinto; }
    public void setPeso(double peso) { this.peso = peso; }
    public void addCambioEstado(CambioEstadoBolsin cambio) { this.cambioEstado.add(cambio); }
}
package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bolsines")
public class Bolsin {
    @Id
    private Integer numeroBolsin;
    private LocalDate fecha;
    private String numeroPrecinto;
    private Double peso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cm_origen_codigo")
    private ComisionMedica comisionMedicaOrigen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cm_destino_codigo")
    private ComisionMedica comisionMedicaDestino;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bolsin_numero")
    private List<CambioEstadoBolsin> cambioEstado = new ArrayList<>();

    public Bolsin() {}

    public Bolsin(Integer numeroBolsin, LocalDate fecha, String numeroPrecinto, Double peso,
                  ComisionMedica cmOrigen, ComisionMedica cmDestino, List<CambioEstadoBolsin> cambioEstado) {
        this.numeroBolsin = numeroBolsin;
        this.fecha = fecha;
        this.numeroPrecinto = numeroPrecinto;
        this.peso = peso;
        this.comisionMedicaOrigen = cmOrigen;
        this.comisionMedicaDestino = cmDestino;
        this.cambioEstado = cambioEstado;
    }

    // Métodos del Diagrama de Clases y Secuencia (Patrón Experto)
    public boolean esEnviado() {
        CambioEstadoBolsin actual = obtenerEstadoActual();
        return actual != null && actual.sosEnviado();
    }

    public CambioEstadoBolsin obtenerEstadoActual() {
        for (CambioEstadoBolsin ceb : this.cambioEstado) {
            if (ceb.sosActual()) {
                return ceb;
            }
        }
        return null;
    }

    /**
     * Respeta el nombre del método en el Diagrama de Clases/Secuencia,
     * validando que el bolsín haya salido de la CM del usuario logueado (ERS Paso 3).
     */
    public boolean esCMUsuarioIgualCMDestino(ComisionMedica cmUsuario) {
        return this.comisionMedicaOrigen != null && this.comisionMedicaOrigen.equals(cmUsuario);
    }

    public String getNumeroDePrecinto() {
        return this.numeroPrecinto;
    }

    public Integer getNumeroBolsin() { return numeroBolsin; }
    public LocalDate getFecha() { return fecha; }
    public Double getPeso() { return peso; }
    public ComisionMedica getComisionMedicaOrigen() { return comisionMedicaOrigen; }
    public ComisionMedica getComisionMedicaDestino() { return comisionMedicaDestino; }
}
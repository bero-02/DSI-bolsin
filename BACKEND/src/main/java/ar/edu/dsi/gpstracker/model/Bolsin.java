package ar.edu.dsi.gpstracker.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bolsin {
    private int numeroBolsin;
    private String codigoComisionMedicaOrigen;
    private String codigoComisionMedicaDestino;
    private int numeroPrecinto;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaHoraUltimaActualizacion;
    

    public Bolsin(Integer numeroBolsin,
                  String codigoComisionMedicaOrigen,
                  String codigoComisionMedicaDestino,
                  int numeroPrecinto,
                  Double latitud,
                  Double longitud,
                  LocalDateTime fechaHoraUltimaActualizacion) {
        this.numeroBolsin = numeroBolsin;
        this.codigoComisionMedicaOrigen = codigoComisionMedicaOrigen;
        this.codigoComisionMedicaDestino = codigoComisionMedicaDestino;
        this.numeroPrecinto = numeroPrecinto;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHoraUltimaActualizacion = fechaHoraUltimaActualizacion;
    }
    private final List<CambioEstadoBolsin> cambiosEstado = new ArrayList<>();

    public void addCambioEstado(CambioEstadoBolsin cambioEstado) {
        cambiosEstado.add(cambioEstado);
    }
    
    public boolean esEnviado() {
        return cambiosEstado.stream()
                .anyMatch(cambio -> cambio.sosActual() && cambio.sosEnviado());
    }
    
    public boolean esCMUsuarioIgualCMDestino(ComisionMedica comisionMedica) {
        return comisionMedica != null
                && comisionMedica.getCodigo() != null
                && codigoComisionMedicaDestino != null
                && codigoComisionMedicaDestino.equalsIgnoreCase(comisionMedica.getCodigo());
    }


    public Integer getNumeroBolsin() {
        return numeroBolsin;
    }

    public String getCodigoComisionMedicaOrigen() {
        return codigoComisionMedicaOrigen;
    }

    public String getCodigoComisionMedicaDestino() {
        return codigoComisionMedicaDestino;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public LocalDateTime getFechaHoraUltimaActualizacion() {
        return fechaHoraUltimaActualizacion;
    }

    public Integer getNumeroDePrecinto() {
        return numeroPrecinto;
    }
}

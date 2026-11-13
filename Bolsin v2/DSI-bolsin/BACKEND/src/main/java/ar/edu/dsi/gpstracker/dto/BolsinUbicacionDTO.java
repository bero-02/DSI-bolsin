package ar.edu.dsi.gpstracker.dto;

public class BolsinUbicacionDTO {
    private Integer numeroBolsin;
    private String numeroPrecinto;
    private String codigoCMOrigen;
    private String comisionMedicaOrigen;
    private String codigoCMDestino;
    private String comisionMedicaDestino;
    private Double latitud;
    private Double longitud;
    private String ultimaActualizacion;

    public BolsinUbicacionDTO() {}

    public BolsinUbicacionDTO(Integer numeroBolsin, String numeroPrecinto, String codigoCMOrigen,
                              String comisionMedicaOrigen, String codigoCMDestino, String comisionMedicaDestino,
                              Double latitud, Double longitud, String ultimaActualizacion) {
        this.numeroBolsin = numeroBolsin;
        this.numeroPrecinto = numeroPrecinto;
        this.codigoCMOrigen = codigoCMOrigen;
        this.comisionMedicaOrigen = comisionMedicaOrigen;
        this.codigoCMDestino = codigoCMDestino;
        this.comisionMedicaDestino = comisionMedicaDestino;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Integer getNumeroBolsin() { return numeroBolsin; }
    public void setNumeroBolsin(Integer numeroBolsin) { this.numeroBolsin = numeroBolsin; }
    public String getNumeroPrecinto() { return numeroPrecinto; }
    public void setNumeroPrecinto(String numeroPrecinto) { this.numeroPrecinto = numeroPrecinto; }
    public String getCodigoCMOrigen() { return codigoCMOrigen; }
    public void setCodigoCMOrigen(String codigoCMOrigen) { this.codigoCMOrigen = codigoCMOrigen; }
    public String getComisionMedicaOrigen() { return comisionMedicaOrigen; }
    public void setComisionMedicaOrigen(String comisionMedicaOrigen) { this.comisionMedicaOrigen = comisionMedicaOrigen; }
    public String getCodigoCMDestino() { return codigoCMDestino; }
    public void setCodigoCMDestino(String codigoCMDestino) { this.codigoCMDestino = codigoCMDestino; }
    public String getComisionMedicaDestino() { return comisionMedicaDestino; }
    public void setComisionMedicaDestino(String comisionMedicaDestino) { this.comisionMedicaDestino = comisionMedicaDestino; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public String getUltimaActualizacion() { return ultimaActualizacion; }
    public void setUltimaActualizacion(String ultimaActualizacion) { this.ultimaActualizacion = ultimaActualizacion; }
}
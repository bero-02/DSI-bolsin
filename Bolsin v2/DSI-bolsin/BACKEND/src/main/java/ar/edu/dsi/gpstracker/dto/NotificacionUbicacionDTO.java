package ar.edu.dsi.gpstracker.dto;

public class NotificacionUbicacionDTO {
    private Integer numeroBolsin;
    private String numeroPrecinto;
    private String comisionMedicaDestino;
    private String emailGCM;
    private String asunto;
    private String cuerpoMensaje;
    private String fechaHoraEnvio;
    private boolean exito;

    public NotificacionUbicacionDTO() {}

    public Integer getNumeroBolsin() { return numeroBolsin; }
    public void setNumeroBolsin(Integer numeroBolsin) { this.numeroBolsin = numeroBolsin; }
    public String getNumeroPrecinto() { return numeroPrecinto; }
    public void setNumeroPrecinto(String numeroPrecinto) { this.numeroPrecinto = numeroPrecinto; }
    public String getComisionMedicaDestino() { return comisionMedicaDestino; }
    public void setComisionMedicaDestino(String comisionMedicaDestino) { this.comisionMedicaDestino = comisionMedicaDestino; }
    public String getEmailGCM() { return emailGCM; }
    public void setEmailGCM(String emailGCM) { this.emailGCM = emailGCM; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getCuerpoMensaje() { return cuerpoMensaje; }
    public void setCuerpoMensaje(String cuerpoMensaje) { this.cuerpoMensaje = cuerpoMensaje; }
    public String getFechaHoraEnvio() { return fechaHoraEnvio; }
    public void setFechaHoraEnvio(String fechaHoraEnvio) { this.fechaHoraEnvio = fechaHoraEnvio; }
    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
}
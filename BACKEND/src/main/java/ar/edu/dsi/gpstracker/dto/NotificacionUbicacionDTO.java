package ar.edu.dsi.gpstracker.dto;

public record NotificacionUbicacionDTO(
        Integer numeroBolsin,
        String codigoComisionMedicaDestino,
        String estado,
        String mensaje
) {
}

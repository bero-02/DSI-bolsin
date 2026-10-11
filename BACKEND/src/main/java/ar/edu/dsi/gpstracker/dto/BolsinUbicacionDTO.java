package ar.edu.dsi.gpstracker.dto;

import java.time.LocalDateTime;

public record BolsinUbicacionDTO(
        Integer numeroBolsin,
        Double latitud,
        Double longitud,
        LocalDateTime fechaHoraUltimaActualizacion
) {
}

package ar.edu.dsi.gpstracker.controller;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/gps/xtr-4500l")
public class Xtr4500lController {

    private static final Map<Integer, double[]> COORDENADAS_EN_RUTA = Map.of(
            101, new double[]{-32.4102, -62.0051}, // En Ruta Autopista Córdoba-Rosario (Altura Marcos Juárez)
            102, new double[]{-33.1232, -64.3493}, // En Ruta hacia Mendoza (Altura Río Cuarto)
            103, new double[]{-33.8891, -60.5736}  // En Ruta hacia CABA (Altura Pergamino)
    );

    @GetMapping("/getBolsinLocation")
    public BolsinUbicacionDTO getBolsinLocation(
            @RequestParam String apiKey,
            @RequestParam Integer numeroBolsin,
            @RequestParam String codigoCMOrigen) {

        double[] coords = COORDENADAS_EN_RUTA.getOrDefault(numeroBolsin, new double[]{-31.4201, -64.1888});
        String ahora = LocalDateTime.now().minusMinutes(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        BolsinUbicacionDTO dto = new BolsinUbicacionDTO();
        dto.setNumeroBolsin(numeroBolsin);
        dto.setCodigoCMOrigen(codigoCMOrigen);
        dto.setLatitud(coords[0]);
        dto.setLongitud(coords[1]);
        dto.setUltimaActualizacion(ahora);
        return dto;
    }
}
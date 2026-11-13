package ar.edu.dsi.gpstracker.interfaces;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.model.Bolsin;
import java.util.List;

public interface InterfazGPSTracker {
    List<BolsinUbicacionDTO> solicitarDatosLocalizacion(List<Bolsin> bolsinesEnviados, String apiKey);
}
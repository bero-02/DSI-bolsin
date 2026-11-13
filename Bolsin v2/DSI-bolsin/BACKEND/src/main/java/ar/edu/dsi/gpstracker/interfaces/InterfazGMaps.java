package ar.edu.dsi.gpstracker.interfaces;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.VisualizacionMapaDTO;
import java.util.List;

public interface InterfazGMaps {
    VisualizacionMapaDTO mostrarMapa(String nombreCM, String usuario, List<BolsinUbicacionDTO> localizaciones);
}
package ar.edu.dsi.gpstracker.controller;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.NotificacionUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.VisualizacionMapaDTO;
import ar.edu.dsi.gpstracker.service.GestorUbicacionBolsines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ubicacion-bolsines")
public class GestorUbicacionBolsinesController {

    private final GestorUbicacionBolsines gestor;

    public GestorUbicacionBolsinesController(GestorUbicacionBolsines gestor) {
        this.gestor = gestor;
    }

    @GetMapping("/consultar")
    public ResponseEntity<VisualizacionMapaDTO> tomarOpConsultarUbicacion() {
        return ResponseEntity.ok(gestor.tomarOpConsultarUbicacion());
    }

    @GetMapping("/seleccionar/{numeroBolsin}")
    public ResponseEntity<BolsinUbicacionDTO> tomarSeleccionBolsin(@PathVariable Integer numeroBolsin) {
        return ResponseEntity.ok(gestor.tomarSeleccionBolsin(numeroBolsin));
    }

    @PostMapping("/notificar-gcm/{numeroBolsin}")
    public ResponseEntity<NotificacionUbicacionDTO> tomarConfirmacionEnvioMailGCM(@PathVariable Integer numeroBolsin) {
        return ResponseEntity.ok(gestor.tomarConfirmacionEnvioMailGCM(numeroBolsin));
    }
}
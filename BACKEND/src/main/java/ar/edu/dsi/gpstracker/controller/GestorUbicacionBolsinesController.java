package ar.edu.dsi.gpstracker.controller;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.NotificacionUbicacionDTO;
import ar.edu.dsi.gpstracker.service.GestorUbicacionBolsines;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gestor/ubicacion/bolsines")
public class GestorUbicacionBolsinesController {

    private final GestorUbicacionBolsines gestor;

    public GestorUbicacionBolsinesController(GestorUbicacionBolsines gestor) {
        this.gestor = gestor;
    }

    @GetMapping("/comision-actual")
    public String obtenerComisionActual() {
        gestor.buscarCM();
        return gestor.getSeleccionCM();
    }

    @GetMapping
    public List<BolsinUbicacionDTO> consultarUbicaciones() {
        gestor.tomarOpConsultarUbicacion();
        return gestor.getLocalizacionBolsines();
    }

    @PostMapping("/{numeroBolsin}/notificacion")
    public NotificacionUbicacionDTO notificarUbicacion(@PathVariable Integer numeroBolsin) {
        return gestor.solicitarNotificacionUbicacionBolsin(numeroBolsin);
    }
}

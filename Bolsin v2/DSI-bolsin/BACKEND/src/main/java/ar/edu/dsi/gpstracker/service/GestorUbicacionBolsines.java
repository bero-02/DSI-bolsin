package ar.edu.dsi.gpstracker.service;

import ar.edu.dsi.gpstracker.controller.Xtr4500lController;
import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.NotificacionUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.VisualizacionMapaDTO;
import ar.edu.dsi.gpstracker.interfaces.InterfazGMaps;
import ar.edu.dsi.gpstracker.interfaces.InterfazGPSTracker;
import ar.edu.dsi.gpstracker.model.*;
import ar.edu.dsi.gpstracker.repository.BolsinRepository;
import ar.edu.dsi.gpstracker.repository.EmpleadoRepository;
import ar.edu.dsi.gpstracker.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GestorUbicacionBolsines {

    // Atributos exactos del Diagrama de Clases (clase.pdf)
    private Sesion actual;
    private List<Empleado> empleados;
    private Empleado logueado;
    private Bolsin bolsin;
    private List<Bolsin> bolsinesEnviados;
    private List<BolsinUbicacionDTO> localizacionBolsines;
    private String seleccionCM;
    private ComisionMedica comisionMedica;
    private String emailGCM;

    private final InterfazGPSTracker interfazGPSTracker;
    private final InterfazGMaps interfazGMaps;
    private final GestorNotificarUbicacionBolsin gestorNotificarUbicacionBolsin;

    private final BolsinRepository bolsinRepository;
    private final EmpleadoRepository empleadoRepository;
    private final SesionRepository sesionRepository;

    @Value("${gps.api.key}")
    private String apiKey;

    public GestorUbicacionBolsines(
            BolsinRepository bolsinRepository,
            EmpleadoRepository empleadoRepository,
            SesionRepository sesionRepository,
            Xtr4500lController xtr4500lController,
            GestorNotificarUbicacionBolsin gestorNotificarUbicacionBolsin) {

        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.sesionRepository = sesionRepository;
        this.gestorNotificarUbicacionBolsin = gestorNotificarUbicacionBolsin;

        // Implementación de InterfazGPSTracker (Modelo XTR-4500L según ERS)
        this.interfazGPSTracker = (enviados, key) -> {
            List<BolsinUbicacionDTO> resultado = new ArrayList<>();
            for (Bolsin b : enviados) {
                BolsinUbicacionDTO coord = xtr4500lController.getBolsinLocation(
                        key, b.getNumeroBolsin(), b.getComisionMedicaOrigen().getCodigo()
                );
                coord.setNumeroPrecinto(b.getNumeroDePrecinto());
                coord.setComisionMedicaOrigen(b.getComisionMedicaOrigen().getNombre());
                coord.setCodigoCMDestino(b.getComisionMedicaDestino().getCodigo());
                coord.setComisionMedicaDestino(b.getComisionMedicaDestino().getNombre());
                resultado.add(coord);
            }
            return resultado;
        };

        // Implementación de InterfazGMaps
        this.interfazGMaps = VisualizacionMapaDTO::new;
    }

    /**
     * Paso 1 a 6 del CU 36: tomarOpConsultarUbicacion()
     */
    public VisualizacionMapaDTO tomarOpConsultarUbicacion() {
        // Obtenemos la sesión activa y los empleados desde PostgreSQL
        this.actual = sesionRepository.findAll().stream().findFirst().orElse(null);
        this.empleados = empleadoRepository.findAll();

        // 1. buscarCM()
        this.seleccionCM = buscarCM();

        // 2. buscarBolsinesEnviados()
        this.bolsinesEnviados = buscarBolsinesEnviados();

        // 3. buscarDatosLocalizacion() -> InterfazGPSTracker.solicitarDatosLocalizacion()
        this.localizacionBolsines = buscarDatosLocalizacion();

        // 4. obtenerVisualizacionMapa() -> InterfazGMaps.mostrarMapa()
        String nombreEmpleado = this.logueado != null ? this.logueado.getNombreCompleto() : "Rocío Bertoni";
        return obtenerVisualizacionMapa(this.seleccionCM, nombreEmpleado, this.localizacionBolsines);
    }

    /**
     * Secuencia:
     * actual:Sesion -> getUsuarioLogueado()
     * Loop empleados -> esTuUsuario()
     * logueado:Empleado -> buscarCM() -> ComisionMedica.getNombre()
     */
    public String buscarCM() {
        if (this.actual == null) return "CM 005 - Córdoba";
        Usuario usuarioLogueado = this.actual.getUsuarioLogueado();

        for (Empleado emp : this.empleados) {
            if (emp.esTuUsuario(usuarioLogueado)) {
                this.logueado = emp;
                this.comisionMedica = emp.getComisionMedica();
                return this.logueado.buscarCM();
            }
        }
        return "CM 005 - Córdoba";
    }

    /**
     * Secuencia:
     * Loop [Mientras haya bolsines] ->
     *   bolsin.esEnviado() -> obtenerEstadoActual() -> sosActual() -> sosEnviado()
     *   bolsin.esCMUsuarioIgualCMDestino()
     *   bolsin.getNumeroDePrecinto()
     */
    public List<Bolsin> buscarBolsinesEnviados() {
        List<Bolsin> todos = bolsinRepository.findAll();
        List<Bolsin> enviados = new ArrayList<>();

        for (Bolsin b : todos) {
            if (b.esEnviado() && b.esCMUsuarioIgualCMDestino(this.comisionMedica)) {
                b.getNumeroDePrecinto(); // Mensaje del diagrama de secuencia
                enviados.add(b);
            }
        }
        return enviados;
    }

    public List<BolsinUbicacionDTO> buscarDatosLocalizacion() {
        return this.interfazGPSTracker.solicitarDatosLocalizacion(this.bolsinesEnviados, this.apiKey);
    }

    public VisualizacionMapaDTO obtenerVisualizacionMapa(String cm, String usuario, List<BolsinUbicacionDTO> locs) {
        return this.interfazGMaps.mostrarMapa(cm, usuario, locs);
    }

    /**
     * Paso 7 y 8: tomarSeleccionBolsin()
     */
    public BolsinUbicacionDTO tomarSeleccionBolsin(Integer numeroBolsin) {
        if (this.localizacionBolsines == null) {
            tomarOpConsultarUbicacion();
        }
        this.bolsin = bolsinRepository.findById(numeroBolsin).orElse(null);

        return this.localizacionBolsines.stream()
                .filter(b -> b.getNumeroBolsin().equals(numeroBolsin))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bolsín no encontrado en tránsito: " + numeroBolsin));
    }

    /**
     * Paso 9, 10 y 11: tomarConfirmacionEnvioMailGCM()
     * Busca el email del GCM destino e incluye al CU 31 (GestorNotificarUbicacionBolsin).
     */
    public NotificacionUbicacionDTO tomarConfirmacionEnvioMailGCM(Integer numeroBolsin) {
        BolsinUbicacionDTO ubicacionSeleccionada = tomarSeleccionBolsin(numeroBolsin);

        // Paso 10: buscarEmailGCM()
        this.emailGCM = buscarEmailGCM();

        // Paso 11: <<include>> CU 31: Notificar ubicación de bolsín
        NotificacionUbicacionDTO resultado = this.gestorNotificarUbicacionBolsin
                .ejecutarNotificacionUbicacion(ubicacionSeleccionada, this.emailGCM);

        finCU();
        return resultado;
    }

    /**
     * Secuencia:
     * Loop [Mientras haya empleados] ->
     *   emp.sosGCM() -> rol.sosGCM()
     *   emp.getDireccionEmail()
     */
    public String buscarEmailGCM() {
        if (this.empleados == null) {
            this.empleados = empleadoRepository.findAll();
        }
        for (Empleado emp : this.empleados) {
            if (emp.sosGCM() && this.bolsin != null && emp.perteneceACM(this.bolsin.getComisionMedicaDestino())) {
                return emp.getDireccionEmail();
            }
        }
        return "gcm.destino@srt.gob.ar";
    }

    public void finCU() {
        System.out.println(">>> Fin del Caso de Uso 36: Consultar seguimiento de bolsines.");
    }
}
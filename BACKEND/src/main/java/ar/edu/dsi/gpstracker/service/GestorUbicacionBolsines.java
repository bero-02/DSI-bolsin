package ar.edu.dsi.gpstracker.service;

import java.util.ArrayList;
import java.util.List;

import ar.edu.dsi.gpstracker.model.Bolsin;
import ar.edu.dsi.gpstracker.model.ComisionMedica;
import ar.edu.dsi.gpstracker.model.Empleado;
import ar.edu.dsi.gpstracker.model.Usuario;
import ar.edu.dsi.gpstracker.repository.InMemoryBolsinRepository;

public class GestorUbicacionBolsines {
    
    public interface InterfazGPSTracker {
        String solicitarDatosLocalizacion(Bolsin bolsin);
    }
    
    public interface InterfazGMaps {
        void mostrarMapa(String localizacion);
    }
    private String localizacionBolsines;

    // Dependencias inyectadas por Spring Boot
    private final InMemoryBolsinRepository bolsinRepository;
    private final InterfazGPSTracker interfazGPSTracker;
    private final InterfazGMaps interfazGMaps;
    
    // Atributos de estado del caso de uso (según diseño DSI)
    private Empleado logeadoEmpleado;
    private ComisionMedica comisionMedica;
    private List<Bolsin> bolsinesEnviados;
    private List<Integer> nrosPrecintos;
    private String emailGCM;

    // Inyección de dependencias mediante el constructor
    public GestorUbicacionBolsines(InMemoryBolsinRepository bolsinRepository, 
                                   InterfazGPSTracker interfazGPSTracker, 
                                   InterfazGMaps interfazGMaps) {
        this.bolsinRepository = bolsinRepository;
        this.interfazGPSTracker = interfazGPSTracker;
        this.interfazGMaps = interfazGMaps;
    }

    // ====================================================================
    // FLUJO DEL DIAGRAMA DE SECUENCIA
    // ====================================================================

    /**
     * Mensaje 1: tomarOpConsultarUbicacionBolsin()
     * Nota: En Spring Boot, recibimos el usuario logueado y la lista de empleados 
     * (o su repositorio) por parámetro desde el Controller.
     */
    public List<Bolsin> tomarOpConsultarUbicacionBolsin(Usuario usuarioLogeado, List<Empleado> todosLosEmpleados) {
        // Inicializamos las colecciones para este ciclo
        this.bolsinesEnviados = new ArrayList<>();
        this.nrosPrecintos = new ArrayList<>();

        buscarCM(usuarioLogeado, todosLosEmpleados);
        
        // Si encontró la CM del usuario, busca los bolsines
        if (this.comisionMedica != null) {
            buscarBolsinesEnviados();
        }

        // Retornamos los bolsines listos para que el Controller / Pantalla los muestre
        return this.bolsinesEnviados;
    }

    /**
     * Mensajes 2 al 4: buscarCM() -> esTuUsuario() -> getComisionMedica() -> getNombre()
     */
    private void buscarCM(Usuario usuarioLogeado, List<Empleado> empleados) {
        for (Empleado emp : empleados) {
            if (emp.esTuUsuario(usuarioLogeado)) {
                this.logeadoEmpleado = emp;
                this.comisionMedica = emp.getComisionMedica();
                
                // Mensaje al objeto CM para obtener su nombre (según diagrama)
                String nombreCM = comisionMedica.getNombre(); 
                System.out.println("Comisión Médica del usuario: " + nombreCM);
                break;
            }
        }
    }

    /**
     * Mensajes 5 al 9: loop bolsines -> esEnviado() -> esCMUsuarioIgualCMDestino() -> getNumeroDePrecinto()
     */
    private void buscarBolsinesEnviados() {
        // Obtenemos todos los bolsines de nuestro repositorio mock
        List<Bolsin> todosLosBolsines = bolsinRepository.findAll();

        for (Bolsin b : todosLosBolsines) {
            if (b.esEnviado()) {
                if (b.esCMUsuarioIgualCMDestino(this.comisionMedica)) {
                    // Mensaje 9: Obtenemos el número de precinto
                    Integer precinto = b.getNumeroDePrecinto();
                    this.nrosPrecintos.add(precinto);
                    
                    // Guardamos el bolsín en la lista de bolsines enviados
                    this.bolsinesEnviados.add(b);
                }
            }
        }
    }
    // ====================================================================
    // MÉTODOS DE INTEGRACIÓN EXTERNA (LOS QUE TE FALTABAN)
    // ====================================================================
    private String buscarDatosLocalizacion(Bolsin bolsinSeleccionado) {
        return interfazGPSTracker.solicitarDatosLocalizacion(bolsinSeleccionado);
    }

    private void obtenerVisualizacionMapa(String localizacion) {
        interfazGMaps.mostrarMapa(localizacion);
    }

    public void tomarSeleccionBolsin(Bolsin bolsinSeleccionado) {
        String localizacion = buscarDatosLocalizacion(bolsinSeleccionado);
        this.localizacionBolsines = localizacion;
        obtenerVisualizacionMapa(localizacion);
    }

    // ====================================================================
    // MÉTODOS ADICIONALES DEL FLUJO
    // ====================================================================


    public void tomarConfirmacionEnvioMailGCM(List<Empleado> todosLosEmpleados) {
        buscarEmailGCM(todosLosEmpleados);
        finCU();
    }

    private void buscarEmailGCM(List<Empleado> empleados) {
        for (Empleado emp : empleados) {
            if (emp.sosGCM()) {
                this.emailGCM = emp.getDireccionEmail();
                // Aquí iría la lógica de envío de email (Include)
                System.out.println("Enviando mail al GCM: " + this.emailGCM);
            }
        }
    }

    private void finCU() {
        System.out.println("Fin del caso de uso Notificar Ubicación de Bolsín.");
    }
    
    // Getters para que el Controller pueda acceder a los resultados
    public List<Integer> getNrosPrecintos() {
        return nrosPrecintos;
    }
}
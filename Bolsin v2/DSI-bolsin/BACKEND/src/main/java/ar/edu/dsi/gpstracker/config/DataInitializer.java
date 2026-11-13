package ar.edu.dsi.gpstracker.config;

import ar.edu.dsi.gpstracker.model.*;
import ar.edu.dsi.gpstracker.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ComisionMedicaRepository cmRepo;
    private final EmpleadoRepository empleadoRepo;
    private final BolsinRepository bolsinRepo;
    private final SesionRepository sesionRepo;

    @PersistenceContext
    private EntityManager em;

    public DataInitializer(ComisionMedicaRepository cmRepo, EmpleadoRepository empleadoRepo,
                           BolsinRepository bolsinRepo, SesionRepository sesionRepo) {
        this.cmRepo = cmRepo;
        this.empleadoRepo = empleadoRepo;
        this.bolsinRepo = bolsinRepo;
        this.sesionRepo = sesionRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (bolsinRepo.count() > 0) return; // Ya están cargados en PostgreSQL

        // 1. Estados
        Estado estCreado = new Estado("Creado", "Bolsín registrado en CM origen");
        Estado estCerrado = new Estado("Cerrado", "Bolsín precintado listo para retiro");
        Estado estEnviado = new Estado("Enviado", "Bolsín en tránsito retirado por el correo");
        Estado estRecibido = new Estado("Recibido en CM destino", "Bolsín recepcionado");
        em.persist(estCreado);
        em.persist(estCerrado);
        em.persist(estEnviado);
        em.persist(estRecibido);

        // 2. Roles
        Rol rolEB = new Rol("Encargado de Bolsines", "Responsable de gestión y seguimiento de bolsines");
        Rol rolGCM = new Rol("GCM", "Gerente de Comisión Médica");
        em.persist(rolEB);
        em.persist(rolGCM);

        // 3. Comisiones Médicas
        ComisionMedica cmCordoba = new ComisionMedica("CM-005", "Av. Colón 123, Córdoba", "cm005@srt.gob.ar", "CM 005 - Córdoba");
        ComisionMedica cmRosario = new ComisionMedica("CM-007", "San Lorenzo 1500, Rosario", "cm007@srt.gob.ar", "CM 007 - Rosario");
        ComisionMedica cmMendoza = new ComisionMedica("CM-004", "Pedro Molina 450, Mendoza", "cm004@srt.gob.ar", "CM 004 - Mendoza");
        ComisionMedica cmCentral = new ComisionMedica("CMC-001", "Bartolomé Mitre 751, CABA", "cmc001@srt.gob.ar", "CMC 001 - Sede Central CABA");
        cmRepo.saveAll(List.of(cmCordoba, cmRosario, cmMendoza, cmCentral));

        // 4. Usuarios y Empleados (Incluye usuario logueado mock y GCMs de cada CM Destino)
        Usuario uLogueado = new Usuario("rbertoni", "123456");
        Empleado empLogueado = new Empleado("Rocío", "Bertoni", "rbertoni@srt.gob.ar", cmCordoba, rolEB, uLogueado);

        Empleado gcmRosario = new Empleado("Carlos", "Gómez", "cgomez.gcm@rosario.srt.gob.ar", cmRosario, rolGCM, new Usuario("cgomez", "1234"));
        Empleado gcmMendoza = new Empleado("María", "López", "mlopez.gcm@mendoza.srt.gob.ar", cmMendoza, rolGCM, new Usuario("mlopez", "1234"));
        Empleado gcmCentral = new Empleado("Alejandro", "Ruiz", "aruiz.gcm@central.srt.gob.ar", cmCentral, rolGCM, new Usuario("aruiz", "1234"));

        empleadoRepo.saveAll(List.of(empLogueado, gcmRosario, gcmMendoza, gcmCentral));

        // 5. Sesión activa (Mock Usuario Logueado)
        Sesion sesionActual = new Sesion(LocalDateTime.now().minusHours(1), null, uLogueado);
        sesionRepo.save(sesionActual);

        // 6. Bolsines con su historial de CambioEstadoBolsin
        // Bolsín 101: Enviado (De Córdoba a Rosario) -> DEBE APARECER EN EL MAPA
        Bolsin b101 = new Bolsin(101, LocalDate.now().minusDays(1), "PREC-8841", 1250.0, cmCordoba, cmRosario, List.of(
                new CambioEstadoBolsin(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(12), estCerrado),
                new CambioEstadoBolsin(LocalDateTime.now().minusHours(12), null, estEnviado)
        ));

        // Bolsín 102: Enviado (De Córdoba a Mendoza) -> DEBE APARECER EN EL MAPA
        Bolsin b102 = new Bolsin(102, LocalDate.now().minusDays(1), "PREC-8842", 980.0, cmCordoba, cmMendoza, List.of(
                new CambioEstadoBolsin(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(8), estCerrado),
                new CambioEstadoBolsin(LocalDateTime.now().minusHours(8), null, estEnviado)
        ));

        // Bolsín 103: Enviado (De Córdoba a Sede Central CABA) -> DEBE APARECER EN EL MAPA
        Bolsin b103 = new Bolsin(103, LocalDate.now(), "PREC-8849", 2100.0, cmCordoba, cmCentral, List.of(
                new CambioEstadoBolsin(LocalDateTime.now().minusHours(5), null, estEnviado)
        ));

        // Bolsín 104: Cerrado (No enviado aún) -> NO DEBE APARECER EN EL MAPA (Prueba filtro esEnviado())
        Bolsin b104 = new Bolsin(104, LocalDate.now(), "PREC-8855", 540.0, cmCordoba, cmRosario, List.of(
                new CambioEstadoBolsin(LocalDateTime.now().minusHours(2), null, estCerrado)
        ));

        bolsinRepo.saveAll(List.of(b101, b102, b103, b104));
        System.out.println(">>> Datos iniciales cargados en PostgreSQL exitosamente.");
    }
}
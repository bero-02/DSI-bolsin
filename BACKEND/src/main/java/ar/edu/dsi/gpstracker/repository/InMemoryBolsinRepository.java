package ar.edu.dsi.gpstracker.repository;

import ar.edu.dsi.gpstracker.model.Bolsin;
import ar.edu.dsi.gpstracker.model.CambioEstadoBolsin;
import ar.edu.dsi.gpstracker.model.Estado;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryBolsinRepository {

    private final List<Bolsin> bolsines = new ArrayList<>();

    @PostConstruct
    void init() {
        // 1. Creamos un par de estados para probar la lógica
        Estado estadoEnviado = new Estado("Enviado", "En tránsito hacia destino");
        Estado estadoCreado = new Estado("Creado", "Armado, esperando envío");

        // 2. Instanciamos los bolsines y les seteamos un estado actual
        Bolsin b101 = new Bolsin(101, "CM-CBA-01", "CM-ROS-02", 99001, -31.4201, -64.1888, LocalDateTime.of(2026, 3, 7, 12, 30, 0));
        b101.addCambioEstado(new CambioEstadoBolsin(1, LocalDateTime.now().minusHours(5), estadoEnviado));
        bolsines.add(b101);

        Bolsin b102 = new Bolsin(102, "CM-ROS-02", "CM-MDZ-03", 99002, -32.9468, -60.6393, LocalDateTime.of(2026, 3, 7, 12, 32, 15));
        b102.addCambioEstado(new CambioEstadoBolsin(2, LocalDateTime.now().minusHours(4), estadoEnviado));
        bolsines.add(b102);

        Bolsin b103 = new Bolsin(103, "CM-MDZ-03", "CM-BA-01", 99003, -32.8895, -68.8458, LocalDateTime.of(2026, 3, 7, 12, 35, 4));
        b103.addCambioEstado(new CambioEstadoBolsin(3, LocalDateTime.now().minusHours(3), estadoCreado)); // Este no pasará el filtro esEnviado()
        bolsines.add(b103);

        Bolsin b104 = new Bolsin(104, "CM-BA-01", "CM-CBA-01", 99004, -34.6037, -58.3816, LocalDateTime.of(2026, 3, 7, 12, 36, 20));
        b104.addCambioEstado(new CambioEstadoBolsin(4, LocalDateTime.now().minusHours(2), estadoEnviado));
        bolsines.add(b104);

        Bolsin b105 = new Bolsin(105, "CM-TUC-01", "CM-SLA-01", 99005, -26.8083, -65.2176, LocalDateTime.of(2026, 3, 7, 12, 40, 31));
        b105.addCambioEstado(new CambioEstadoBolsin(5, LocalDateTime.now().minusHours(1), estadoEnviado));
        bolsines.add(b105);

        Bolsin b106 = new Bolsin(106, "CM-SLA-01", "CM-JUJ-01", 99006, -24.7829, -65.4232, LocalDateTime.of(2026, 3, 7, 12, 43, 18));
        b106.addCambioEstado(new CambioEstadoBolsin(6, LocalDateTime.now().minusHours(1), estadoCreado)); // Este no pasará el filtro
        bolsines.add(b106);

        Bolsin b107 = new Bolsin(107, "CM-NQN-01", "CM-MDQ-01", 99007, -38.9516, -68.0591, LocalDateTime.of(2026, 3, 7, 12, 45, 46));
        b107.addCambioEstado(new CambioEstadoBolsin(7, LocalDateTime.now().minusMinutes(45), estadoEnviado));
        bolsines.add(b107);

        Bolsin b108 = new Bolsin(108, "CM-MDQ-01", "CM-BA-01", 99008, -38.0055, -57.5426, LocalDateTime.of(2026, 3, 7, 12, 47, 2));
        b108.addCambioEstado(new CambioEstadoBolsin(8, LocalDateTime.now().minusMinutes(30), estadoEnviado));
        bolsines.add(b108);

        Bolsin b109 = new Bolsin(109, "CM-LPL-01", "CM-CBA-01", 99009, -34.9215, -57.9545, LocalDateTime.of(2026, 3, 7, 12, 49, 27));
        b109.addCambioEstado(new CambioEstadoBolsin(9, LocalDateTime.now().minusMinutes(20), estadoEnviado));
        bolsines.add(b109);

        Bolsin b110 = new Bolsin(110, "CM-CBA-02", "CM-ROS-02", 99010, -31.4167, -64.1833, LocalDateTime.of(2026, 3, 7, 12, 52, 9));
        b110.addCambioEstado(new CambioEstadoBolsin(10, LocalDateTime.now().minusMinutes(10), estadoEnviado));
        bolsines.add(b110);
    }

    public List<Bolsin> findAll() {
        return List.copyOf(bolsines);
    }

    public Optional<Bolsin> findByNumeroBolsin(Integer numeroBolsin) {
        return bolsines.stream()
                .filter(b -> b.getNumeroBolsin().equals(numeroBolsin))
                .findFirst();
    }
}
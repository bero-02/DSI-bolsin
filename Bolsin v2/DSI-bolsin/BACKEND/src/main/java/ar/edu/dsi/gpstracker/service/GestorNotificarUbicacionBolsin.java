package ar.edu.dsi.gpstracker.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import ar.edu.dsi.gpstracker.dto.BolsinUbicacionDTO;
import ar.edu.dsi.gpstracker.dto.NotificacionUbicacionDTO;

@Service
public class GestorNotificarUbicacionBolsin {

    /**
     * Caso de Uso 31: Notificar ubicación de bolsín.
     * Simula el envío de correo electrónico al Gerente de Comisión Médica (GCM) destino.
     */
    public NotificacionUbicacionDTO ejecutarNotificacionUbicacion(BolsinUbicacionDTO ubicacion, String emailGCM) {
        String fechaHoraEnvio = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        String asunto = "Seguimiento de Bolsín #" + ubicacion.getNumeroBolsin() +
                        " [Precinto: " + ubicacion.getNumeroPrecinto() + "]";

        String cuerpo = String.format(
                "Estimado/a Gerente de Comisión Médica (%s):\n\n" +
                "Por medio del presente se informa la ubicación actual del bolsín en tránsito hacia su dependencia:\n\n" +
                "• Número de Bolsín: %d\n" +
                "• Número de Precinto: %s\n" +
                "• Comisión Médica Origen: %s\n" +
                "• Coordenadas GPS actuales: Latitud %.5f, Longitud %.5f\n" +
                "• Fecha y hora de última actualización GPS: %s\n\n" +
                "Saludos cordiales,\nSistema de Seguimiento de Bolsines - Correo Nacional",
                ubicacion.getComisionMedicaDestino(),
                ubicacion.getNumeroBolsin(),
                ubicacion.getNumeroPrecinto(),
                ubicacion.getComisionMedicaOrigen(),
                ubicacion.getLatitud(),
                ubicacion.getLongitud(),
                ubicacion.getUltimaActualizacion()
        );

        // Simulación del Actor Secundario: Servidor de Correo
        System.out.println("==========================================================");
        System.out.println("[CU 31 - SERVIDOR DE CORREO] Simulando envío de Email...");
        System.out.println("Destinatario (GCM): " + emailGCM);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje:\n" + cuerpo);
        System.out.println("==========================================================");

        NotificacionUbicacionDTO dto = new NotificacionUbicacionDTO();
        dto.setNumeroBolsin(ubicacion.getNumeroBolsin());
        dto.setNumeroPrecinto(ubicacion.getNumeroPrecinto());
        dto.setComisionMedicaDestino(ubicacion.getComisionMedicaDestino());
        dto.setEmailGCM(emailGCM);
        dto.setAsunto(asunto);
        dto.setCuerpoMensaje(cuerpo);
        dto.setFechaHoraEnvio(fechaHoraEnvio);
        dto.setExito(true);

        return dto;
    }
}
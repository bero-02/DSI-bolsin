package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesiones")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_nombre")
    private Usuario usuario;

    public Sesion() {}

    public Sesion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, Usuario usuario) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.usuario = usuario;
    }

    // Método del Diagrama de Secuencia
    public Usuario getUsuarioLogueado() {
        return this.usuario;
    }
}
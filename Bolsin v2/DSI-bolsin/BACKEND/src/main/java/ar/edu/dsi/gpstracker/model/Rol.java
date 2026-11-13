package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    public Rol() {}

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Método del Diagrama de Secuencia
    public boolean sosGCM() {
        return "GCM".equalsIgnoreCase(this.nombre) ||
               "Gerente de Comisión Médica".equalsIgnoreCase(this.nombre);
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
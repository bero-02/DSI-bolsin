package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    private String nombre;
    private String contrasena;

    public Usuario() {}

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
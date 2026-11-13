package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comisiones_medicas")
public class ComisionMedica {
    @Id
    private String codigo;
    private String direccion;
    private String email;
    private String nombre;

    public ComisionMedica() {}

    public ComisionMedica(String codigo, String direccion, String email, String nombre) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.email = email;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComisionMedica that = (ComisionMedica) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
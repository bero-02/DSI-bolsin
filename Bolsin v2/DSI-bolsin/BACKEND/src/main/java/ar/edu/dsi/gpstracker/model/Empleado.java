package ar.edu.dsi.gpstracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cm_codigo")
    private ComisionMedica comisionMedica;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_nombre")
    private Usuario usuario;

    public Empleado() {}

    public Empleado(String nombre, String apellido, String email, ComisionMedica comisionMedica, Rol rol, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.comisionMedica = comisionMedica;
        this.rol = rol;
        this.usuario = usuario;
    }

    // Métodos del Diagrama de Clases y Secuencia
    public boolean esTuUsuario(Usuario u) {
        return this.usuario != null && this.usuario.equals(u);
    }

    public String buscarCM() {
        return this.comisionMedica != null ? this.comisionMedica.getNombre() : null;
    }

    public String getNombreCM() {
        return buscarCM();
    }

    public boolean sosGCM() {
        return this.rol != null && this.rol.sosGCM();
    }

    public String getDireccionEmail() {
        return this.email;
    }

    public boolean perteneceACM(ComisionMedica cm) {
        return this.comisionMedica != null && this.comisionMedica.equals(cm);
    }

    public ComisionMedica getComisionMedica() { return comisionMedica; }
    public String getNombreCompleto() { return nombre + " " + apellido; }
}
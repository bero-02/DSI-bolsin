package ar.edu.dsi.gpstracker.model;

public class ComisionMedica {
    // Se utiliza String para el código, manteniendo coherencia con codigoComisionMedicaOrigen de Bolsin
    private String codigo; 
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    public ComisionMedica(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getDireccion() { return direccion; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
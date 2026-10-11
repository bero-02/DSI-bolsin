package ar.edu.dsi.gpstracker.model;

public class Estado {
    private String nombre;
    private String descripcion;

    public Estado(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA Y CASO DE USO 36
    // ====================================================================

    /**
     * Mensaje 7.2.1: sosEnviado()[cite: 18]
     * El caso de uso 36 en su paso 3 exige buscar los bolsines que se encuentran 
     * en estado "Enviado" para mostrarlos en el mapa[cite: 31]. 
     * Por patrón Experto en Información, la clase Estado es la responsable de 
     * conocer su propio nombre y responder a esta validación.
     */
    public boolean sosEnviado() {
        return "Enviado".equalsIgnoreCase(this.nombre);
    }

    // ====================================================================
    // GETTERS Y SETTERS
    // ====================================================================

    // El nombre define la identidad del estado, por lo que solo se inicializa 
    // en el constructor y se expone su getter (sin setter).
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // La descripción es un detalle informativo que sí podría actualizarse.
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
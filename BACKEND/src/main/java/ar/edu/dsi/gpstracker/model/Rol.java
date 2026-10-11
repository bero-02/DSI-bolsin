package ar.edu.dsi.gpstracker.model;

public class Rol {
    private String nombre;
    private String descripcion;

    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ====================================================================
    // COMPORTAMIENTO SEGÚN DIAGRAMA DE SECUENCIA Y CASO DE USO 36
    // ====================================================================

    /**
     * Requerido por la delegación del mensaje 15: sosGCM() del diagrama de secuencia[cite: 18].
     * El paso 8 y 10 del Caso de Uso 36 indican que se debe buscar y notificar 
     * al Gerente de Comisión Médica (GCM)[cite: 31]. 
     * Por patrón Experto, el Rol es quien sabe si su nombre corresponde a "GCM".
     */
    public boolean esGCM() {
        return "GCM".equalsIgnoreCase(this.nombre);
    }

    // ====================================================================
    // GETTERS Y SETTERS
    // ====================================================================

    public String getNombre() { 
        return nombre; 
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }

    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
}
package ar.edu.dsi.gpstracker.repository;

import java.util.List;
import java.util.Optional;

import ar.edu.dsi.gpstracker.model.Bolsin;

public interface BolsinRepository {
    // Método que usará el Gestor para traer todos y luego iterarlos
    List<Bolsin> obtenerTodos();
    
    // Método extra por si se busca un bolsín en particular (para los endpoints de la API)
    Optional<Bolsin> buscarPorNumero(Integer numeroBolsin);
}

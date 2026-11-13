package ar.edu.dsi.gpstracker.repository;

import ar.edu.dsi.gpstracker.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {}
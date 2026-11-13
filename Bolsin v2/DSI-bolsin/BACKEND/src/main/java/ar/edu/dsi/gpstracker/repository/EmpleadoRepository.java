package ar.edu.dsi.gpstracker.repository;

import ar.edu.dsi.gpstracker.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {}
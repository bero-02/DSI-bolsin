package ar.edu.dsi.gpstracker.repository;

import ar.edu.dsi.gpstracker.model.Bolsin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BolsinRepository extends JpaRepository<Bolsin, Integer> {}
package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden,Integer> {
}

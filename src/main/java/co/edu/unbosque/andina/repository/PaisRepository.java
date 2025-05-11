package co.edu.unbosque.andina.repository;

import co.edu.unbosque.andina.entity.Ciudad;
import co.edu.unbosque.andina.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais,Integer> {
     Pais findByid (int id);
}

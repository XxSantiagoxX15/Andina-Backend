package co.edu.unbosque.andina.repository;

import co.edu.unbosque.andina.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CiudadRepository extends JpaRepository<Ciudad,Integer> {
    List<Ciudad> findByPaisId(Integer paisId);




}

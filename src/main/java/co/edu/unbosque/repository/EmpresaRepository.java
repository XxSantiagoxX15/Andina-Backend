package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa,Integer> {

}

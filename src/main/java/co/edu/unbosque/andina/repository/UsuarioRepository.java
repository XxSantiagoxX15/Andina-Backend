package co.edu.unbosque.andina.repository;

import co.edu.unbosque.andina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}

package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}

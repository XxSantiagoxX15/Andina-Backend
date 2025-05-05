package co.edu.unbosque.andina.repository;

import co.edu.unbosque.andina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByCorreo(String correo);
}

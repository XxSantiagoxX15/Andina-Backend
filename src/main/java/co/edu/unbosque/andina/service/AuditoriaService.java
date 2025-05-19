package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.repository.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaService {
    private  JdbcTemplate jdbcTemplate;
    private  UsuarioRepository usuarioRepository;

    public AuditoriaService(JdbcTemplate jdbcTemplate, UsuarioRepository usuarioRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRepository = usuarioRepository;
    }

    public void setAppUsuarioId() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + correo));
        jdbcTemplate.update("SET @app_usuario_id = ?", usuario.getIdentificacion());
    }
}

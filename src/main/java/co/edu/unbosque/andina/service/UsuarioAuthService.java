package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class UsuarioAuthService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    public UsuarioAuthService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        String role;
        switch (user.getRol()) {
            case 1:
                role = "ROLE_ADMININISTRADOR";
                break;
            case 2:
                role = "ROLE_COMISIONISTA";
                break;
            case 3:
                role = "ROLE_ACCIONISTA";
                break;
            default:
                throw new IllegalArgumentException("Rol no válido: " + user.getRol());
        }

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}

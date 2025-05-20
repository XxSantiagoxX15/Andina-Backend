package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.repository.UsuarioRepository;
import co.edu.unbosque.andina.entity.Rol;
import co.edu.unbosque.andina.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private AuditoriaService auditoriaService;

  @Autowired
  private RolRepository rolRepository;

  public List<Usuario> listarUsuarios() {
    return usuarioRepository.findAll();
  }

  public Usuario buscarPorIdentificacion(Integer identificacion) {
    return usuarioRepository.findById(identificacion)
      .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + identificacion));
  }

  public Usuario buscarPorCorreo(String correo) {
    return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + correo));
  }

  public Usuario guardarUsuario(Usuario usuario) {
    usuario.setCreatedAt(LocalDateTime.now());
    usuario.setUpdateAt(LocalDateTime.now());
    Usuario nuevo = usuarioRepository.save(usuario);

    // Obtener el nombre del usuario autenticado
    String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

    // Registrar en auditoría
    auditoriaService.registrarAuditoria("CREAR", "Creacion de un usuario","");

    return nuevo;
  }

  public Usuario actualizarUsuario(Integer identificacion, Usuario usuarioActualizado) {
    Usuario usuarioExistente = buscarPorIdentificacion(identificacion);

    usuarioExistente.setPrimerNombre(usuarioActualizado.getPrimerNombre());
    usuarioExistente.setSegundoNombre(usuarioActualizado.getSegundoNombre());
    usuarioExistente.setPrimerApellido(usuarioActualizado.getPrimerApellido());
    usuarioExistente.setSegundoApellido(usuarioActualizado.getSegundoApellido());
    usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
    usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
    usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
    usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
    usuarioExistente.setNumeroLicencia(usuarioActualizado.getNumeroLicencia());
    usuarioExistente.setRol(usuarioActualizado.getRol());
    usuarioExistente.setSaldo(usuarioActualizado.getSaldo());
    usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
    usuarioExistente.setUpdateAt(LocalDateTime.now());
    String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

    // Registrar en auditoría
    auditoriaService.registrarAuditoria("ACTUALIZAR", "Actualizacion de un usuario"+ usuarioExistente.getCorreo(),usuarioAccion);
    return usuarioRepository.save(usuarioExistente);
  }

  public void eliminarUsuario(Integer identificacion) {
    Usuario usuario = buscarPorIdentificacion(identificacion);
    usuario.setDeletedAt(LocalDateTime.now());

    String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

    // Registrar en auditoría
    auditoriaService.registrarAuditoria("BORRADO LOGICO", "BORRADO LOGICO DE USUARIO"+ usuario.getCorreo(),usuarioAccion);
    usuarioRepository.save(usuario); // Borrado lógico
  }

  public void eliminarUsuarioPermanentemente(Integer identificacion) {
    Usuario usuario = usuarioRepository.findById(identificacion).
            orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + identificacion));
    if (!usuarioRepository.existsById(identificacion)) {
      throw new EntityNotFoundException("Usuario no encontrado con ID: " + identificacion);
    }
    String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

    // Registrar en auditoría
    auditoriaService.registrarAuditoria("BORRADO", "BORRADO DE USUARIO"+ usuario.getCorreo(),usuarioAccion);
    usuarioRepository.deleteById(identificacion); // Borrado físico
  }
}

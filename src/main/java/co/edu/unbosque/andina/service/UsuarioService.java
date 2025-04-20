package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.repository.UsuarioRepository;
import co.edu.unbosque.andina.entity.Rol;
import co.edu.unbosque.andina.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private RolRepository rolRepository;

  public List<Usuario> listarUsuarios() {
    return usuarioRepository.findAll();
  }

  public Usuario buscarPorIdentificacion(Integer identificacion) {
    return usuarioRepository.findById(identificacion)
      .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + identificacion));
  }

  public Usuario guardarUsuario(Usuario usuario) {
    usuario.setCreatedAt(LocalDateTime.now());
    usuario.setUpdateAt(LocalDateTime.now());
    return usuarioRepository.save(usuario);
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
    usuarioExistente.setCiudad(usuarioActualizado.getCiudad());
    usuarioExistente.setUpdateAt(LocalDateTime.now());

    return usuarioRepository.save(usuarioExistente);
  }

  public void eliminarUsuario(Integer identificacion) {
    Usuario usuario = buscarPorIdentificacion(identificacion);
    usuario.setDeletedAt(LocalDateTime.now());
    usuarioRepository.save(usuario); // Borrado lógico
  }

  public void eliminarUsuarioPermanentemente(Integer identificacion) {
    if (!usuarioRepository.existsById(identificacion)) {
      throw new EntityNotFoundException("Usuario no encontrado con ID: " + identificacion);
    }
    usuarioRepository.deleteById(identificacion); // Borrado físico
  }
}

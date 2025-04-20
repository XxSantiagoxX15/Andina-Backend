package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  @Operation(summary = "Crear Usuario", description = "Crea un nuevo usuario con un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario creado correctamente", content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
    try {
      usuarioService.guardarUsuario(usuario);
      return ResponseEntity.ok("Usuario creado exitosamente.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al crear el usuario: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Listar Usuarios", description = "Retorna todos los usuarios registrados.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "500", description = "Error al obtener usuarios")
  })
  public ResponseEntity<List<Usuario>> listarUsuarios() {
    return ResponseEntity.ok(usuarioService.listarUsuarios());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener Usuario", description = "Retorna un usuario por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Integer id) {
    try {
      Usuario usuario = usuarioService.buscarPorIdentificacion(id);
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar Usuario", description = "Actualiza los datos de un usuario.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error al actualizar el usuario")
  })
  public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioNuevo) {
    try {
      Usuario actualizado = usuarioService.actualizarUsuario(id, usuarioNuevo);
      return ResponseEntity.ok(actualizado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar Usuario (lógico)", description = "Marca como eliminado un usuario (no lo borra físicamente).")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario marcado como eliminado"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error al eliminar usuario")
  })
  public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
    try {
      usuarioService.eliminarUsuario(id);
      return ResponseEntity.ok("Usuario eliminado lógicamente.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }
  }

  @DeleteMapping("/hard/{id}")
  @Operation(summary = "Eliminar Usuario (físico)", description = "Elimina completamente un usuario.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario eliminado permanentemente"),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error al eliminar usuario")
  })
  public ResponseEntity<String> eliminarUsuarioFisico(@PathVariable Integer id) {
    try {
      usuarioService.eliminarUsuarioPermanentemente(id);
      return ResponseEntity.ok("Usuario eliminado permanentemente.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }
  }
}

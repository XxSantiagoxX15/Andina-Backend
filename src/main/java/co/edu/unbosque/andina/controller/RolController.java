package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Rol;
import co.edu.unbosque.andina.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

  @Autowired
  private RolService rolService;

  @PostMapping
  @Operation(summary = "Crear Rol", description = "Crea un nuevo rol.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Rol creado exitosamente"),
    @ApiResponse(responseCode = "500", description = "Error interno al crear el rol")
  })
  public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
    try {
      Rol nuevoRol = rolService.saveRol(rol);
      return ResponseEntity.ok(nuevoRol);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar Rol", description = "Actualiza los datos de un rol existente.")
  public ResponseEntity<Rol> updateRol(@PathVariable int id, @RequestBody Rol rol) {
    Rol rolActualizado = rolService.updateRol(id, rol);
    if (rolActualizado != null) {
      return ResponseEntity.ok(rolActualizado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar Rol", description = "Elimina un rol por su ID.")
  public ResponseEntity<String> deleteRol(@PathVariable int id) {
    try {
      boolean deleted = rolService.deleteRol(id);
      if (deleted) {
        return ResponseEntity.ok("Rol eliminado exitosamente.");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al eliminar el rol: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todos los roles", description = "Obtiene una lista de todos los roles.")
  public ResponseEntity<List<Rol>> getAllRoles() {
    List<Rol> roles = rolService.findAll();
    return ResponseEntity.ok(roles);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol por su ID.")
  public ResponseEntity<Rol> getRolById(@PathVariable int id) {
    return rolService.findAll()
      .stream()
      .filter(rol -> rol.getId() == id)
      .findFirst()
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

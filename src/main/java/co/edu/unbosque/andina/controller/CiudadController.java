package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Ciudad;
import co.edu.unbosque.andina.entity.Pais;
import co.edu.unbosque.andina.repository.PaisRepository;
import co.edu.unbosque.andina.service.CiudadService;
import co.edu.unbosque.andina.service.PaisService;
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
import java.util.Optional;

@Transactional
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:8080","http://localhost:4100", "*"})
@RestController
@RequestMapping("/auth")
public class CiudadController {

  @Autowired
  private CiudadService ciudadService;
  @Autowired
  private PaisService paisService;

  @PostMapping
  @Operation(summary = "Crear Ciudad", description = "Crea una nueva ciudad con su respectivo país.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Ciudad creada exitosamente", content = @Content(schema = @Schema(implementation = Ciudad.class))),
          @ApiResponse(responseCode = "500", description = "Error interno al crear la ciudad")
  })
  public ResponseEntity<?> createCiudad(@RequestBody Ciudad ciudad) {
    try {
      // Validar nombre y pais_id
      if (ciudad.getNombre() == null || ciudad.getNombre().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("El nombre de la ciudad es obligatorio.");
      }

      if (ciudad.getPaisId() == 0) {
        return ResponseEntity.badRequest().body("El ID del país es obligatorio y debe ser distinto de cero.");
      }

      // Verificar existencia del país
      Optional<Pais> paisExistente = paisService.obtenerPorId(ciudad.getPaisId());
      if (paisExistente.isEmpty()) {
        return ResponseEntity.badRequest().body("El país con ID " + ciudad.getPaisId() + " no existe.");
      }

      // Guardar la ciudad
      Ciudad nueva = ciudadService.createCiudad(ciudad);
      return ResponseEntity.ok(nueva);

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error al crear ciudad: " + e.getMessage());
    }
  }

  @GetMapping("/ciudad/pais/{paisId}")
  public ResponseEntity<List<Ciudad>> obtenerCiudadesPorPais(@PathVariable int paisId) {
    List<Ciudad> ciudades = ciudadService.obtenerCiudadesPorPais(paisId);
    return new ResponseEntity<>(ciudades, HttpStatus.OK);
  }

  @GetMapping
  @Operation(summary = "Listar todas las ciudades", description = "Devuelve todas las ciudades registradas.")
  public ResponseEntity<List<Ciudad>> getAllCiudades() {
    return ResponseEntity.ok(ciudadService.getAllCiudades());
  }

  @GetMapping("/ciudad/nombre/{id}")
  @Operation(summary = "Obtener nombre de ciudad por ID", description = "Devuelve el nombre de una ciudad dado su ID.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Nombre de la ciudad encontrado"),
          @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
  })
  public ResponseEntity<String> getNombreCiudadById(@PathVariable int id) {
    String nombreCiudad = ciudadService.getCiudadNombreById(id);

    if ("Ciudad no encontrada".equals(nombreCiudad)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nombreCiudad);
    }

    return ResponseEntity.ok(nombreCiudad);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar ciudad", description = "Actualiza los datos de una ciudad existente.")
  public ResponseEntity<?> updateCiudad(@PathVariable int id, @RequestBody Ciudad ciudadActualizada) {
    Optional<Ciudad> existente = ciudadService.getCiudadById(id);
    if (existente.isPresent()) {
      ciudadActualizada.setId(id);
      return ResponseEntity.ok(ciudadService.updateCiudad(ciudadActualizada));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad por su ID.")
  public ResponseEntity<String> deleteCiudad(@PathVariable int id) {
    Optional<Ciudad> existente = ciudadService.getCiudadById(id);
    if (existente.isPresent()) {
      ciudadService.deleteCiudad(id);
      return ResponseEntity.ok("Ciudad eliminada exitosamente");
    }
    return ResponseEntity.notFound().build();
  }
}

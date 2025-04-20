package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Ciudad;
import co.edu.unbosque.andina.entity.Pais;
import co.edu.unbosque.andina.repository.PaisRepository;
import co.edu.unbosque.andina.service.CiudadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:8080", "*"})
@RestController
@RequestMapping("/ciudades")
public class CiudadController {

  @Autowired
  private CiudadService ciudadService;
  @Autowired
  private PaisRepository paisRepository;

  @PostMapping
  @Operation(summary = "Crear Ciudad", description = "Crea una nueva ciudad con su respectivo país.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Ciudad creada exitosamente", content = @Content(schema = @Schema(implementation = Ciudad.class))),
    @ApiResponse(responseCode = "500", description = "Error interno al crear la ciudad")
  })
  public ResponseEntity<?> createCiudad(@RequestBody Ciudad ciudad) {
    try {
      // Verificar si el país existe
      if (ciudad.getPais() != null && ciudad.getPais().getId() != 0) {
        Pais paisExistente = paisRepository.findById(ciudad.getPais().getId())
          .orElseThrow(() -> new RuntimeException("País no encontrado con ID: " + ciudad.getPais().getId()));

        // Asignamos el país encontrado a la ciudad
        ciudad.setPais(paisExistente);
      }

      // Guardar la ciudad
      Ciudad nueva = ciudadService.createCiudad(ciudad);
      return ResponseEntity.ok(nueva);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error al crear ciudad: " + e.getMessage());
    }
  }


  @GetMapping
  @Operation(summary = "Listar todas las ciudades", description = "Devuelve todas las ciudades registradas.")
  public ResponseEntity<List<Ciudad>> getAllCiudades() {
    return ResponseEntity.ok(ciudadService.getAllCiudades());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener ciudad por ID", description = "Devuelve una ciudad si existe con el ID proporcionado.")
  public ResponseEntity<Ciudad> getCiudadById(@PathVariable int id) {
    Optional<Ciudad> ciudad = ciudadService.getCiudadById(id);
    return ciudad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

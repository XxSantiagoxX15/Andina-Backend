package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Pais;
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
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RestController
@RequestMapping("/pais")
public class PaisController {

  @Autowired
  private PaisService paisService;

  @PostMapping
  @Operation(summary = "Crear país", description = "Crea un país con los datos proporcionados en formato JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "País creado exitosamente", content = @Content(schema = @Schema(implementation = Pais.class))),
    @ApiResponse(responseCode = "500", description = "Error al crear el país")
  })
  public ResponseEntity<String> crearPais(@RequestBody Pais pais) {
    try {
      paisService.crearPais(pais);
      return ResponseEntity.ok("País creado exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al crear el país: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Listar todos los países", description = "Devuelve todos los países registrados.")
  public ResponseEntity<List<Pais>> obtenerTodos() {
    List<Pais> paises = paisService.obtenerTodos();
    return ResponseEntity.ok(paises);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener país por ID", description = "Devuelve un país si existe con el ID proporcionado.")
  public ResponseEntity<Pais> obtenerPorId(@PathVariable int id) {
    Optional<Pais> pais = paisService.obtenerPorId(id);
    return pais.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar país", description = "Actualiza los datos de un país existente.")
  public ResponseEntity<Pais> actualizarPais(@PathVariable int id, @RequestBody Pais nuevoPais) {
    Optional<Pais> existente = paisService.obtenerPorId(id);
    if (existente.isPresent()) {
      nuevoPais.setId(id); // Asegura que se actualiza el país correcto
      Pais actualizado = paisService.actualizarPais(nuevoPais);
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar país", description = "Elimina un país por su ID.")
  public ResponseEntity<String> eliminarPais(@PathVariable int id) {
    Optional<Pais> existente = paisService.obtenerPorId(id);
    if (existente.isPresent()) {
      paisService.eliminarPais(id);
      return ResponseEntity.ok("País eliminado exitosamente");
    }
    return ResponseEntity.notFound().build();
  }
}

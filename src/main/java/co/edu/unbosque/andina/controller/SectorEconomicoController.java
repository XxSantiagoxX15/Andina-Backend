package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.SectorEconomico;
import co.edu.unbosque.andina.service.SectorEconomicoService;
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
@RequestMapping("/sector-economico")
public class SectorEconomicoController {

  @Autowired
  private SectorEconomicoService sectorService;

  @PostMapping
  @Operation(summary = "Crear Sector Económico", description = "Crea un sector económico basado en un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sector económico creado exitosamente", content = @Content(schema = @Schema(implementation = SectorEconomico.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createSector(@RequestBody SectorEconomico sector) {
    try {
      sectorService.createSectorEconomico(sector);
      return ResponseEntity.ok("Sector económico creado exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al crear el sector económico: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Listar todos los sectores económicos", description = "Devuelve todos los sectores económicos registrados.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sectores encontrados", content = @Content(schema = @Schema(implementation = SectorEconomico.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar los sectores económicos")
  })
  public ResponseEntity<List<SectorEconomico>> getAllSectores() {
    List<SectorEconomico> sectores = sectorService.getAllSectoresEconomicos();
    return ResponseEntity.ok(sectores);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener sector económico por ID", description = "Devuelve un sector económico si existe con el ID proporcionado.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sector encontrado"),
    @ApiResponse(responseCode = "404", description = "Sector no encontrado")
  })
  public ResponseEntity<SectorEconomico> getSectorById(@PathVariable int id) {
    Optional<SectorEconomico> sector = sectorService.getSectorEconomico(id);
    return sector.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar sector económico", description = "Actualiza los datos de un sector económico existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sector actualizado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Sector no encontrado")
  })
  public ResponseEntity<SectorEconomico> updateSector(@PathVariable int id, @RequestBody SectorEconomico nuevoSector) {
    Optional<SectorEconomico> existente = sectorService.getSectorEconomico(id);
    if (existente.isPresent()) {
      nuevoSector.setId(id); // Asegurar que se actualiza el correcto
      SectorEconomico actualizado = sectorService.updateSectorEconomico(nuevoSector);
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar sector económico", description = "Elimina un sector económico por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Sector eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Sector no encontrado")
  })
  public ResponseEntity<String> deleteSector(@PathVariable int id) {
    Optional<SectorEconomico> existente = sectorService.getSectorEconomico(id);
    if (existente.isPresent()) {
      sectorService.deleteSectorEconomico(id);
      return ResponseEntity.ok("Sector económico eliminado exitosamente");
    }
    return ResponseEntity.notFound().build();
  }
}

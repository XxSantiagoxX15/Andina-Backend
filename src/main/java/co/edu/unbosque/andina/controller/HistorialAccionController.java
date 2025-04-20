package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Historial_Accion;
import co.edu.unbosque.andina.service.HistorialAccionService;
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
@RequestMapping("/historial-accion")
public class HistorialAccionController {

  @Autowired
  private HistorialAccionService historialAccionService;


  @PostMapping
  @Operation(summary = "Crear Historial de Acción", description = "Crea un registro de historial de acción con un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial creado exitosamente", content = @Content(schema = @Schema(implementation = Historial_Accion.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createHistorial(@RequestBody Historial_Accion historialAccion) {
    try {
      historialAccionService.createHistorialAccion(historialAccion);
      return ResponseEntity.ok("Historial de acción creado exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar el historial de acción: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todos los historiales de acción", description = "Retorna la lista completa de historiales de acción.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historiales de acción encontrados", content = @Content(schema = @Schema(implementation = Historial_Accion.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar los historiales de acción")
  })
  public ResponseEntity<List<Historial_Accion>> getAllHistoriales() {
    List<Historial_Accion> historialAccionList = historialAccionService.getAllHistorialAcciones();
    return ResponseEntity.ok(historialAccionList);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener historial de acción por ID", description = "Busca un historial de acción por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial de acción encontrado"),
    @ApiResponse(responseCode = "404", description = "Historial de acción no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Historial_Accion> getHistorialById(@PathVariable Integer id) {
    Optional<Historial_Accion> historialAccion = historialAccionService.getHistorialAccion(id);
    return historialAccion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar historial de acción", description = "Actualiza un historial de acción existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial de acción actualizado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Historial de acción no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Historial_Accion> updateHistorial(@PathVariable Integer id, @RequestBody Historial_Accion historialNuevo) {
    Historial_Accion actualizado = historialAccionService.updateHistorialAccion(historialNuevo, id);
    if (actualizado != null) {
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar historial de acción", description = "Elimina un historial de acción por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial de acción eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Historial de acción no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteHistorial(@PathVariable Integer id) {
    boolean eliminado = historialAccionService.deleteHistorialAccion(id);
    if (eliminado) {
      return ResponseEntity.ok("Historial de acción eliminado con éxito");
    }
    return ResponseEntity.notFound().build();
  }
}

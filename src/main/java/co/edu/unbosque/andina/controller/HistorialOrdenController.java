package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Historial_Orden;
import co.edu.unbosque.andina.service.HistorialOrdenService;
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
@RequestMapping("/historial-orden")
public class HistorialOrdenController {

  @Autowired
  private HistorialOrdenService historialOrdenService;

  @PostMapping
  @Operation(summary = "Crear Historial de Orden", description = "Crea un registro de historial de orden con un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial creado exitosamente", content = @Content(schema = @Schema(implementation = Historial_Orden.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createHistorial(@RequestBody Historial_Orden historialOrden) {
    try {
      historialOrdenService.createHistorialOrden(historialOrden);
      return ResponseEntity.ok("Historial de orden creado exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar el historial de orden: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todos los historiales", description = "Retorna la lista completa de historiales de orden.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historiales encontrados", content = @Content(schema = @Schema(implementation = Historial_Orden.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar los historiales")
  })
  public ResponseEntity<List<Historial_Orden>> getAllHistoriales() {
    List<Historial_Orden> historialList = historialOrdenService.getAllHistorialOrdenes();
    return ResponseEntity.ok(historialList);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener historial por ID", description = "Busca un historial de orden por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial encontrado"),
    @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Historial_Orden> getHistorialById(@PathVariable Integer id) {
    Optional<Historial_Orden> historial = historialOrdenService.getHistorialOrden(id);
    return historial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar historial", description = "Actualiza un historial de orden existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial actualizado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Historial_Orden> updateHistorial(@PathVariable Integer id, @RequestBody Historial_Orden historialNuevo) {
    Historial_Orden actualizado = historialOrdenService.updateHistorialOrden(historialNuevo, id);
    if (actualizado != null) {
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar historial", description = "Elimina un historial de orden por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Historial eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteHistorial(@PathVariable Integer id) {
    boolean eliminado = historialOrdenService.deleteHistorialOrden(id);
    if (eliminado) {
      return ResponseEntity.ok("Historial de orden eliminado con éxito");
    }
    return ResponseEntity.notFound().build();
  }
}

package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Orden;
import co.edu.unbosque.andina.service.OrdenService;
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
@RequestMapping("/orden")
public class OrdenController {

  @Autowired
  private OrdenService ordenService;

  @PostMapping
  @Operation(summary = "Crear Orden", description = "Crea una orden de acuerdo a un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orden creada exitosamente", content = @Content(schema = @Schema(implementation = Orden.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createOrden(@RequestBody Orden orden) {
    try {
      ordenService.createOrden(orden);
      return ResponseEntity.ok("Orden creada exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar la orden: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todas las órdenes", description = "Retorna la lista completa de órdenes.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Órdenes encontradas", content = @Content(schema = @Schema(implementation = Orden.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar las órdenes")
  })
  public ResponseEntity<List<Orden>> getAllOrdenes() {
    List<Orden> ordenes = ordenService.getAllOrdenes();
    return ResponseEntity.ok(ordenes);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener orden por ID", description = "Busca una orden por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orden encontrada"),
    @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Orden> getOrdenById(@PathVariable Integer id) {
    Optional<Orden> orden = ordenService.getOrden(id);
    return orden.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar orden", description = "Actualiza una orden existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Orden> updateOrden(@PathVariable Integer id, @RequestBody Orden ordenNuevo) {
    Optional<Orden> ordenExistente = ordenService.getOrden(id);
    if (ordenExistente.isPresent()) {
      Orden actualizada = ordenService.updateOrden(ordenNuevo, ordenExistente.get());
      return ResponseEntity.ok(actualizada);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar orden", description = "Elimina una orden por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Orden eliminada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Orden no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteOrden(@PathVariable Integer id) {
    Orden eliminada = ordenService.deleteOrden(id);
    if (eliminada != null) {
      return ResponseEntity.ok("Orden eliminada: " + eliminada.toString());
    }
    return ResponseEntity.notFound().build();
  }
}

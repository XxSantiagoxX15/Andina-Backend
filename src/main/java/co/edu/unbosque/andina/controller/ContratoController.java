package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Contrato;
import co.edu.unbosque.andina.service.ContratoService;
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
@RequestMapping("/contrato")
public class ContratoController {

  @Autowired
  private ContratoService contratoService;

  @PostMapping
  @Operation(summary = "Crear Contrato", description = "Crea un contrato de acuerdo a un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contrato creado exitosamente", content = @Content(schema = @Schema(implementation = Contrato.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createContrato(@RequestBody Contrato contrato) {
    try {
      contratoService.createContrato(contrato);
      return ResponseEntity.ok("Contrato creado exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar el contrato: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todos los contratos", description = "Retorna la lista completa de contratos.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contratos encontrados", content = @Content(schema = @Schema(implementation = Contrato.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar los contratos")
  })
  public ResponseEntity<List<Contrato>> getAllContratos() {
    List<Contrato> contratos = contratoService.getAllContratos();
    return ResponseEntity.ok(contratos);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener contrato por ID", description = "Busca un contrato por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
    @ApiResponse(responseCode = "404", description = "Contrato no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Contrato> getContratoById(@PathVariable Integer id) {
    Optional<Contrato> contrato = contratoService.getContrato(id);
    return contrato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar contrato", description = "Actualiza un contrato existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contrato actualizado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Contrato no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Contrato> updateContrato(@PathVariable Integer id, @RequestBody Contrato contratoNuevo) {
    Optional<Contrato> contratoExistente = contratoService.getContrato(id);
    if (contratoExistente.isPresent()) {
      Contrato actualizado = contratoService.updateContrato(contratoNuevo, contratoExistente.get());
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar contrato", description = "Elimina un contrato por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Contrato eliminado exitosamente"),
    @ApiResponse(responseCode = "404", description = "Contrato no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteContrato(@PathVariable Integer id) {
    Contrato eliminado = contratoService.deleteContrato(id);
    if (eliminado != null) {
      return ResponseEntity.ok("Contrato eliminado: " + eliminado.toString());
    }
    return ResponseEntity.notFound().build();
  }
}

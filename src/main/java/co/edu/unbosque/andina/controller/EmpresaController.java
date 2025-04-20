package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.service.EmpresaService;
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
@RequestMapping("/empresa")
public class EmpresaController {

  @Autowired
  private EmpresaService empresaService;

  @PostMapping
  @Operation(summary = "Crear Empresa", description = "Crea una empresa de acuerdo a un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Empresa creada exitosamente", content = @Content(schema = @Schema(implementation = Empresa.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createEmpresa(@RequestBody Empresa empresa) {
    try {
      empresaService.createEmpresa(empresa);
      return ResponseEntity.ok("Empresa creada exitosamente");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar la empresa: " + e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "Obtener todas las empresas", description = "Retorna la lista completa de empresas.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Empresas encontradas", content = @Content(schema = @Schema(implementation = Empresa.class))),
    @ApiResponse(responseCode = "500", description = "Error al recuperar las empresas")
  })
  public ResponseEntity<List<Empresa>> getAllEmpresas() {
    List<Empresa> empresas = empresaService.getAllEmpresas();
    return ResponseEntity.ok(empresas);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener empresa por ID", description = "Busca una empresa por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Empresa> getEmpresaById(@PathVariable int id) {
    Optional<Empresa> empresa = empresaService.getEmpresa(id);
    return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar empresa", description = "Actualiza una empresa existente.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Empresa actualizada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Empresa> updateEmpresa(@PathVariable int id, @RequestBody Empresa empresaNuevo) {
    Optional<Empresa> empresaExistente = empresaService.getEmpresa(id);
    if (empresaExistente.isPresent()) {
      Empresa actualizado = empresaService.updateEmpresa(empresaNuevo, id);
      return ResponseEntity.ok(actualizado);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar empresa", description = "Elimina una empresa por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Empresa eliminada exitosamente"),
    @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteEmpresa(@PathVariable int id) {
    boolean eliminado = empresaService.deleteEmpresa(id);
    if (eliminado) {
      return ResponseEntity.ok("Empresa eliminada exitosamente");
    }
    return ResponseEntity.notFound().build();
  }
}

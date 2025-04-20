package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Accion;
import co.edu.unbosque.andina.service.AccionService;
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

@Transactional
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RestController
@RequestMapping("/action")
public class AccionController {

  @Autowired
  private AccionService accionService;

  @PostMapping()
  @Operation(summary = "Crear Accion", description = "Crea una accion de acuerdo a un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = Accion.class))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> createAccion(@RequestBody Accion accion) {
    try {
      this.accionService.creataAccion(accion);
      return ResponseEntity.ok("Se insertó la categoría");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error al guardar la categoría: " + e.getMessage());
    }
  }

  @GetMapping()
  @Operation(summary = "Crear Accion", description = "Crea una accion de acuerdo a un cuerpo JSON.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Acciones encontradas", content = @Content(schema = @Schema(implementation = Accion.class))),
    @ApiResponse(responseCode = "500", description = "Error de acciones")
  })
  public ResponseEntity<List<Accion>> getAllAccion() {
    List <Accion> acciones = accionService.getAllAcciones();
    if (acciones != null) {
      return ResponseEntity.ok(acciones);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener accion ", description = "Obtener una accion por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Accion obtenida xitosamente"),
    @ApiResponse(responseCode = "404", description = "Accion no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Accion> getAccionById(@PathVariable Integer id){
      Accion accion = this.accionService.getAccion(id);
      if(accion != null) {
        return ResponseEntity.ok(accion);
      }
      return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  @Operation(summary = "Editar accion ", description = "Obtener una accion por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Accion obtenida xitosamente"),
    @ApiResponse(responseCode = "404", description = "Accion no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<Accion> getAccionById(@PathVariable Integer id , @RequestBody Accion accionNew){
    Accion accion = this.accionService.getAccion(id);
    if(accion != null) {
      if( this.accionService.updateAccion(accionNew , accion) != null) {
        return ResponseEntity.ok(accionNew);
      }
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.notFound().build();
  }


  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar accion ", description = "Eliminar una accion por su ID.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Accion eliminada xitosamente"),
    @ApiResponse(responseCode = "404", description = "Accion no encontrada"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  public ResponseEntity<String> deleteAccionById(@PathVariable Integer id){
    Accion accion = this.accionService.deleteAccion(id);
    if ( accion != null){
        return ResponseEntity.ok(accion.toString());
      }
    return ResponseEntity.notFound().build();
  }
}

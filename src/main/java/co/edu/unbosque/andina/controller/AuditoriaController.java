package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Auditoria;
import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:8080","http://localhost:4100", "*"})
@RestController
@RequestMapping("/admin/auditoria")
public class AuditoriaController {

    private AuditoriaService auditoriaService;
    @GetMapping
    @Operation(summary = "Obtener todas la auditoria", description = "Retorna la lista completa de auditoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auditoria encontradas", content = @Content(schema = @Schema(implementation = Auditoria.class))),
            @ApiResponse(responseCode = "500", description = "Error al recuperar la auditoria")
    })
    public ResponseEntity<List<Auditoria>> getAllAuditoria() {
        List<Auditoria> auditoria = auditoriaService.getAllAuditoria();
        return ResponseEntity.ok(auditoria);
    }
}

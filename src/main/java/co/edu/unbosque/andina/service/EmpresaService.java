package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private AuditoriaService auditoriaService;

  public Empresa createEmpresa(Empresa empresa) {

    String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

    // Registrar en auditoría
    auditoriaService.registrarAuditoria("CREAR", "Creacion de una empresa"+ empresa.getNombre(),usuarioAccion);
    return empresaRepository.save(empresa);
  }

  public Optional<Empresa> getEmpresa(int id) {
    return empresaRepository.findById(id);
  }

  public List<Empresa> getAllEmpresas() {
    return empresaRepository.findAll();
  }

  public Empresa updateEmpresa(Empresa newEmpresa, int id) {
    Optional<Empresa> oldEmpresa = empresaRepository.findById(id);
    if (oldEmpresa.isPresent()) {
      Empresa empresaToUpdate = oldEmpresa.get();
      empresaToUpdate.setNombre(newEmpresa.getNombre());
      empresaToUpdate.setDescripcion(newEmpresa.getDescripcion());
      empresaToUpdate.setSector_economico_id(newEmpresa.getSector_economico_id());

      String usuarioAccion = SecurityContextHolder.getContext().getAuthentication().getName();

      // Registrar en auditoría
      auditoriaService.registrarAuditoria("ACTUALIZAR", "Actualizacion de una empresa"+ newEmpresa.getNombre(),usuarioAccion);
      return empresaRepository.save(empresaToUpdate);
    }
    return null;
  }

  public boolean deleteEmpresa(int id) {
    Optional<Empresa> empresaOptional = empresaRepository.findById(id);

    if (empresaOptional.isPresent()) {
      Empresa empresa = empresaOptional.get();

      // Obtener el usuario autenticado
      String usuarioAccion = "ANÓNIMO";
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
        usuarioAccion = auth.getName();
      }

      // Registrar en auditoría
      auditoriaService.registrarAuditoria(
              "BORRADO",
              "Borrado de empresa con nombre: " + empresa.getNombre(),
              usuarioAccion
      );

      // Eliminar la empresa
      empresaRepository.delete(empresa);
      return true;
    }

    return false;
  }
}

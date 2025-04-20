package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

  public Empresa createEmpresa(Empresa empresa) {
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
      empresaToUpdate.setSectorEconomico(newEmpresa.getSectorEconomico());
      return empresaRepository.save(empresaToUpdate);
    }
    return null;
  }

  public boolean deleteEmpresa(int id) {
    Optional<Empresa> empresa = empresaRepository.findById(id);
    if (empresa.isPresent()) {
      empresaRepository.delete(empresa.get());
      return true;
    }
    return false;
  }
}

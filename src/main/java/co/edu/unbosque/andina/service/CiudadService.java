package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Ciudad;
import co.edu.unbosque.andina.repository.CiudadRepository;
import co.edu.unbosque.andina.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {

  @Autowired
  private CiudadRepository ciudadRepository;

  @Autowired
  private PaisRepository paisRepository;

  public Ciudad createCiudad(Ciudad ciudad) {
    // Verificamos si el país existe antes de guardar
    if (ciudad.getPais() != null && ciudad.getPais().getId() != 0) {
      paisRepository.findById(ciudad.getPais().getId())
        .orElseThrow(() -> new RuntimeException("País no encontrado con ID: " + ciudad.getPais().getId()));
    }
    return ciudadRepository.save(ciudad);
  }

  public List<Ciudad> getAllCiudades() {
    return ciudadRepository.findAll();
  }

  public Optional<Ciudad> getCiudadById(int id) {
    return ciudadRepository.findById(id);
  }

  public Ciudad updateCiudad(Ciudad ciudad) {
    return ciudadRepository.save(ciudad);
  }

  public void deleteCiudad(int id) {
    ciudadRepository.deleteById(id);
  }
}

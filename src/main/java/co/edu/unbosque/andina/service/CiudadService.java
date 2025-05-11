package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Ciudad;
import co.edu.unbosque.andina.repository.CiudadRepository;
import co.edu.unbosque.andina.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    if (ciudad.getNombre()!= null && ciudad.getId()!= 0) {
      paisRepository.findById(ciudad.getId())
        .orElseThrow(() -> new RuntimeException("País no encontrado con ID: " + ciudad.getId()));
    }
    return ciudadRepository.save(ciudad);
  }


  public List<Ciudad> obtenerCiudadesPorPais(int paisId) {
    return ciudadRepository.findByPaisId(paisId);
  }


  public List<Ciudad> getAllCiudades() {
    return ciudadRepository.findAll();
  }

  public Optional<Ciudad> getCiudadById(int id) {

    return ciudadRepository.findById(id);
  }
  public String getCiudadNombreById(int id) {
    return ciudadRepository.findById(id)
            .map(Ciudad::getNombre)
            .orElse("Ciudad no encontrada");
  }

  public Ciudad updateCiudad(Ciudad ciudad) {
    return ciudadRepository.save(ciudad);
  }

  public void deleteCiudad(int id) {
    ciudadRepository.deleteById(id);
  }
}

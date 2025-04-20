package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Pais;
import co.edu.unbosque.andina.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

  @Autowired
  private PaisRepository paisRepository;

  public Pais crearPais(Pais pais) {
    return paisRepository.save(pais);
  }

  public List<Pais> obtenerTodos() {
    return paisRepository.findAll();
  }

  public Optional<Pais> obtenerPorId(int id) {
    return paisRepository.findById(id);
  }

  public Pais actualizarPais(Pais pais) {
    return paisRepository.save(pais);
  }

  public void eliminarPais(int id) {
    paisRepository.deleteById(id);
  }
}

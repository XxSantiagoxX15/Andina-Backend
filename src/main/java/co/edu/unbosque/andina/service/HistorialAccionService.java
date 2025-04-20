package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Historial_Accion;
import co.edu.unbosque.andina.repository.Historial_AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialAccionService {

  @Autowired
  private Historial_AccionRepository historialAccionRepository;

  public Historial_Accion createHistorialAccion(Historial_Accion historialAccion) {
    return historialAccionRepository.save(historialAccion);
  }

  public Optional<Historial_Accion> getHistorialAccion(int id) {
    return historialAccionRepository.findById(id);
  }

  public List<Historial_Accion> getAllHistorialAcciones() {
    return historialAccionRepository.findAll();
  }

  public Historial_Accion updateHistorialAccion(Historial_Accion newHistorialAccion, int id) {
    Optional<Historial_Accion> oldHistorialAccion = historialAccionRepository.findById(id);
    if (oldHistorialAccion.isPresent()) {
      Historial_Accion historialToUpdate = oldHistorialAccion.get();
      historialToUpdate.setNombre(newHistorialAccion.getNombre());
      historialToUpdate.setValor(newHistorialAccion.getValor());
      historialToUpdate.setFecha_hora(newHistorialAccion.getFecha_hora());
      return historialAccionRepository.save(historialToUpdate);
    }
    return null;
  }

  public boolean deleteHistorialAccion(int id) {
    Optional<Historial_Accion> historialAccion = historialAccionRepository.findById(id);
    if (historialAccion.isPresent()) {
      historialAccionRepository.delete(historialAccion.get());
      return true;
    }
    return false;
  }
}

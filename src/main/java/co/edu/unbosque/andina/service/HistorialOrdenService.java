package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Historial_Orden;
import co.edu.unbosque.andina.repository.Historial_OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialOrdenService {

  @Autowired
  private Historial_OrdenRepository historialOrdenRepository;

  public Historial_Orden createHistorialOrden(Historial_Orden historialOrden) {
    return historialOrdenRepository.save(historialOrden);
  }

  public Optional<Historial_Orden> getHistorialOrden(int id) {
    return historialOrdenRepository.findById(id);
  }

  public List<Historial_Orden> getAllHistorialOrdenes() {
    return historialOrdenRepository.findAll();
  }

  public Historial_Orden updateHistorialOrden(Historial_Orden newHistorialOrden, int id) {
    Optional<Historial_Orden> oldHistorialOrden = historialOrdenRepository.findById(id);
    if (oldHistorialOrden.isPresent()) {
      Historial_Orden historialToUpdate = oldHistorialOrden.get();
      historialToUpdate.setPrecio(newHistorialOrden.getPrecio());
      historialToUpdate.setTipo_orden(newHistorialOrden.getTipo_orden());
      historialToUpdate.setFecha_hora(newHistorialOrden.getFecha_hora());
      historialToUpdate.setComision(newHistorialOrden.getComision());
      return historialOrdenRepository.save(historialToUpdate);
    }
    return null;
  }

  public boolean deleteHistorialOrden(int id) {
    Optional<Historial_Orden> historialOrden = historialOrdenRepository.findById(id);
    if (historialOrden.isPresent()) {
      historialOrdenRepository.delete(historialOrden.get());
      return true;
    }
    return false;
  }
}

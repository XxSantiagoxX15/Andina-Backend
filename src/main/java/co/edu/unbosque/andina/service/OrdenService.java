package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Orden;
import co.edu.unbosque.andina.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

  @Autowired
  private OrdenRepository ordenRepository;

  // Crear una nueva orden
  public Orden createOrden(Orden orden) {
    return ordenRepository.save(orden);
  }

  // Obtener una orden por su ID
  public Optional<Orden> getOrden(Integer idOrden) {
    return ordenRepository.findById(idOrden);
  }

  // Obtener todas las órdenes
  public List<Orden> getAllOrdenes() {
    return ordenRepository.findAll();
  }

  // Actualizar una orden existente
  public Orden updateOrden(Orden newOrden, Orden oldOrden) {
    oldOrden.setTipo_orden(newOrden.getTipo_orden());
    oldOrden.setPrecio(newOrden.getPrecio());
    oldOrden.setFecha_hora(newOrden.getFecha_hora());
    oldOrden.setComision(newOrden.getComision());
    return ordenRepository.save(oldOrden);
  }

  // Eliminar una orden
  public Orden deleteOrden(Integer idOrden) {
    Optional<Orden> orden = ordenRepository.findById(idOrden);
    if (orden.isPresent()) {
      ordenRepository.delete(orden.get());
      return orden.get();
    }
    return null;
  }
}

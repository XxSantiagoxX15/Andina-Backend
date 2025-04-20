package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.SectorEconomico;
import co.edu.unbosque.andina.repository.SectorEconomicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorEconomicoService {

  @Autowired
  private SectorEconomicoRepository sectorEconomicoRepository;

  /**
   * Obtener todos los sectores económicos.
   *
   * @return Lista de sectores económicos.
   */
  public List<SectorEconomico> getAllSectoresEconomicos() {
    return sectorEconomicoRepository.findAll();
  }

  /**
   * Obtener un sector económico por su ID.
   *
   * @param id ID del sector económico.
   * @return Sector económico si existe, o Optional vacío.
   */
  public Optional<SectorEconomico> getSectorEconomico(int id) {
    return sectorEconomicoRepository.findById(id);
  }

  /**
   * Crear un nuevo sector económico.
   *
   * @param sector_economico Sector económico a crear.
   * @return El sector económico creado.
   */
  public SectorEconomico createSectorEconomico(SectorEconomico sector_economico) {
    return sectorEconomicoRepository.save(sector_economico);
  }

  /**
   * Actualizar un sector económico existente.
   *
   * @param sector_economico Sector económico con los datos actualizados.
   * @return El sector económico actualizado.
   */
  public SectorEconomico updateSectorEconomico(SectorEconomico sector_economico) {
    return sectorEconomicoRepository.save(sector_economico);
  }

  /**
   * Eliminar un sector económico por su ID.
   *
   * @param id ID del sector económico a eliminar.
   */
  public void deleteSectorEconomico(int id) {
    sectorEconomicoRepository.deleteById(id);
  }
}

package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Contrato;
import co.edu.unbosque.andina.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

  @Autowired
  private ContratoRepository contratoRepository;

  public Contrato createContrato(Contrato contrato) {
    return contratoRepository.save(contrato);
  }

  public Optional<Contrato> getContrato(Integer idContrato) {
    return contratoRepository.findById(idContrato);
  }

  public List<Contrato> getAllContratos() {
    return contratoRepository.findAll();
  }

  public Contrato updateContrato(Contrato newContrato, Contrato oldContrato) {
    oldContrato.setFechaHoraInicio(newContrato.getFechaHoraInicio());
    oldContrato.setFechaHoraFin(newContrato.getFechaHoraFin());
    oldContrato.setComision(newContrato.getComision());
    return contratoRepository.save(oldContrato);
  }


  public Contrato deleteContrato(Integer idContrato) {
    Optional<Contrato> contrato = contratoRepository.findById(idContrato);
    if (contrato.isPresent()) {
      contratoRepository.delete(contrato.get());
      return contrato.get();
    }
    return null;
  }
}

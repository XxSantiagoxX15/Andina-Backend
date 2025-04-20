package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Accion;
import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.repository.AccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AccionService {

  @Autowired
  private AccionRepository accionRepository;

  public Accion creataAccion(Accion accion){
    return accionRepository.save(accion);
  }

  public Accion getAccion(Integer idAccion){
    return accionRepository.findById(idAccion).get();
  }

  public List<Accion> getAllAcciones(){
    return accionRepository.findAll();
  }

  public List<Accion> getAccionesByEmpresas(Empresa empresa){
    ArrayList<Accion> accionesPorEmpresas = new ArrayList<Accion>();
    for(Accion accion : accionRepository.findAll()){
          if (accion.getId() == empresa.getId()){
              accionesPorEmpresas.add(accion);
          }
        }
    return accionesPorEmpresas;
  }

  public Accion updateAccion(Accion accion , Accion oldAccion){
    accionRepository.deleteById(oldAccion.getId());
    return accionRepository.save(accion);
  }

  public Accion deleteAccion(Integer idAccion){
    Accion accion = accionRepository.findById(idAccion).get();
    if(accion != null) {
      accionRepository.delete(accion);
      return accion;
    }
    return null;

  }


}

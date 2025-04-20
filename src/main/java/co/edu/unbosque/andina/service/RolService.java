package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Rol;
import co.edu.unbosque.andina.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RolService {
    private final Logger logger = LoggerFactory.getLogger(Rol.class);

    @Autowired
    private RolRepository rolRepository;

    public Rol saveRol(Rol rol){

        Rol rolEntity= rolRepository.save(rol);

        return rolEntity;

    }

    public Rol updateRol(int id, Rol rol){
        if(rolRepository.existsById(id)){
            rol.setId(id);
            return rolRepository.save(rol);
        }else{
            return null;
        }
    }

    public Boolean deleteRol(int id){
        if(rolRepository.existsById(id)){
            rolRepository.deleteById(id);
            return true;

        }else{
            throw  new EntityNotFoundException("El rol  con ID"+id +"no existe.");
        }

    }


    public List<Rol> findAll(){
        return rolRepository.findAll();
    }
}







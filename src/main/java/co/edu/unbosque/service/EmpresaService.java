package co.edu.unbosque.service;

import co.edu.unbosque.entity.Empresa;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.EmpresaRepository;
import co.edu.unbosque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;


    public Empresa saveEmpresa(Empresa empresa){

        Empresa empresaEntity= empresaRepository.save(empresa);

        return empresaEntity;

    }


    public Empresa updateEmpresa(int id, Empresa empresa){
        if(empresaRepository.existsById(id)){
            empresa.setId(id);
            return empresaRepository.save(empresa);
        }else{
            return null;
        }
    }


    public List<Empresa> findAll(){
        return empresaRepository.findAll();
    }
}

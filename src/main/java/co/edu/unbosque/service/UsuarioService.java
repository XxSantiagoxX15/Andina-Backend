package co.edu.unbosque.service;

import co.edu.unbosque.entity.Rol;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario saveUsario(Usuario usuario){

        Usuario usuarioEntity= usuarioRepository.save(usuario);

        return usuarioEntity;

    }


    public Usuario updateUsuario(int id, Usuario usuario){
        if(usuarioRepository.existsById(id)){
            usuario.setIdentificacion(id);
            return usuarioRepository.save(usuario);
        }else{
            return null;
        }
    }


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

}

package co.edu.unbosque.andina.service;

import co.edu.unbosque.andina.entity.Auditoria;
import co.edu.unbosque.andina.entity.Empresa;
import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.repository.AuditoriaRepository;
import co.edu.unbosque.andina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public void registrarAuditoria(String accion, String descripcion, String nombre) {
        Auditoria audit = new Auditoria();
        audit.setAccion(accion);
        audit.setDescripcion(descripcion);
        audit.setFechaHora(LocalDateTime.now());
        audit.setUsuario(nombre); // Puede ser null si aplica

        auditoriaRepository.save(audit);
    }


    public List<Auditoria> getAllAuditoria() {
        return auditoriaRepository.findAll();
    }
}

package co.edu.unbosque.andina.controller;

import co.edu.unbosque.andina.entity.Usuario;
import co.edu.unbosque.andina.service.CiudadService;
import co.edu.unbosque.andina.service.UsuarioService;
import co.edu.unbosque.andina.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@Transactional
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RestController
@RequestMapping("/auth")
public class UsuarioAuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CiudadService ciudadService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword())
        );

        Usuario user = usuarioService.buscarPorCorreo(authRequest.getCorreo());
        String token = jwtUtil.generateToken(user.getCorreo(), user.getRol());
        System.out.println(token + ciudadService.getCiudadNombreById(user.getCiudad())+ user.getPrimerNombre()+ user.getPrimerApellido());
        return ResponseEntity.ok(new AuthResponse(token ,ciudadService.getCiudadNombreById(user.getCiudad()),user.getRol(),user.getPrimerNombre(),user.getPrimerApellido()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario user) {
        System.out.println(user.getContrasena());
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        usuarioService.guardarUsuario(user);
        return ResponseEntity.ok("Usuario registrado");
    }
}

class AuthRequest {
    private String correo;
    private String password;


    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }


}

class AuthResponse {
    private String token;
    private String ciudadId;
    private String primerNombre;
    private String primerApellido;
    private int id;

    public AuthResponse(String token, String ciudadId,int id,String primernombre,String primerapellido) {
        this.token = token;
        this.ciudadId = ciudadId;
        this.primerApellido=primerapellido;
        this.primerNombre=primernombre;
        this.id=id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package co.edu.unbosque.andina.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; // Expiración en milisegundos

    public String generateToken(String usuario, int rol) {
        return JWT.create()
                .withSubject(usuario)
                .withClaim("rol", rol)
                .withIssuedAt(new Date()) // Fecha de emisión
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration)) // Fecha de expiración
                .sign(Algorithm.HMAC512(secret));
    }


    private DecodedJWT getClaimsFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token);
    }


    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }


    public int getRolFromToken(String token) {
        return getClaimsFromToken(token).getClaim("rol").asInt();
    }


    public boolean validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            // Verifica si el token no está expirado
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate != null && expirationDate.after(new Date());
        } catch (JWTVerificationException e) {
            // Si la firma es incorrecta o el token está expirado, se captura la excepción
            return false;
        }
    }
}


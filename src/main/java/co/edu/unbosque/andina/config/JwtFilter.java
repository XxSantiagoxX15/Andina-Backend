package co.edu.unbosque.andina.config;

import co.edu.unbosque.andina.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

@Autowired
    private JwtUtil jwtUtil;
@Autowired
    private UserDetailsService userDetailsService;
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/auth/**","/all/**"
    );
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Si la solicitud es para Swagger o autenticación, no procesamos el JWT
        if (EXCLUDED_PATHS.stream().anyMatch(path -> requestURI.startsWith(path))) {
            filterChain.doFilter(request, response);  // Permite que la solicitud pase sin filtrar el JWT
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Verifica que el encabezado de autorización esté presente y que empiece con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // El token es la parte después de "Bearer "
            username = jwtUtil.getUsernameFromToken(token);
        }

        // Si el username no es nulo y no hay una autenticación existente en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Cargar los detalles del usuario desde el servicio de detalles
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Si el token es válido, procede con la autenticación
                if (jwtUtil.validateToken(token)) {
                    // Crear un objeto de autenticación con los roles extraídos del token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Establecer los detalles de la autenticación
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Colocar la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Manejar cualquier error que ocurra al cargar el usuario o validar el token
                logger.error("Error al autenticar al usuario: " + username, e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o error de autenticación");
                return;
            }
        }

        // Continuar con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }

}
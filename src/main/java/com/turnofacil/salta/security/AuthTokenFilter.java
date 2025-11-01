package com.turnofacil.salta.security;

import com.turnofacil.salta.service.impl.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Obtenemos el token de la cabecera
            String jwt = parseJwt(request);

            // 2. Si hay token y es válido...
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // 3. Obtenemos el email (username) del token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // 4. Cargamos los detalles del usuario desde la BD
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Creamos un objeto de autenticación
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // No se necesitan credenciales (password) aquí
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. ¡Autenticamos al usuario para esta petición!
                // Guardamos la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("No se pudo establecer la autenticación del usuario: {}", e.getMessage());
        }

        // 7. Continuamos con el resto de los filtros y la petición
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera "Authorization".
     * Espera un formato: "Bearer [token]"
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Devuelve solo el token
        }

        return null;
    }
}

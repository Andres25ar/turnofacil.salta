package com.turnofacil.salta.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

//import static jdk.internal.org.jline.keymap.KeyMap.key;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${turnofacil.jwt.secret}")
    private String jwtSecret;

    @Value("${turnofacil.jwt.expiration}")
    private long jwtExpirationMs;

    public String generateJwtToken (Authentication authentication){
        // Obtenemos el usuario principal (nuestro UserDetailsImpl)
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Construimos el token
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Guardamos el email en el "subject"
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //obtiene el username desde un token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Valida un token JWT.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("El string de claims de JWT está vacío: {}", e.getMessage());
        }
        return false;
    }
}

package com.turnofacil.salta.config;

import com.turnofacil.salta.security.AuthEntryPointJwt;
import com.turnofacil.salta.security.AuthTokenFilter;
import com.turnofacil.salta.service.impl.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Activa la configuración de seguridad web de Spring
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // Le dice a Spring cómo debe verificar la autenticación
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService); // Usa nuestro servicio para buscar usuarios
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa BCrypt para contraseñas

        return authProvider;
    }

    // El "cerebro" que maneja la autenticación. Lo usaremos en el AuthService.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // El filtro principal de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Le decimos que use nuestro manejador de errores 401
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // Le decimos que NO cree sesiones. Será 100% stateless (basado en tokens).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configuración de las rutas
                .authorizeHttpRequests(authz -> authz
                        // Permitimos el acceso público a nuestras rutas de auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // ¡TAMBIÉN AÑADIMOS ESTO PARA SEGUIR PROBANDO EL CRUD!
                        .requestMatchers("/api/v1/health-centers/**").permitAll()
                        //permisos para crear una especialidad
                        .requestMatchers("/api/v1/specialities/**").permitAll()
                        //permisos para crear una disponibilidad horaria
                        .requestMatchers("/api/v1/schedules/**").permitAll()
                        // Cualquier otra ruta (que no tengamos aún) requerirá autenticación
                        .anyRequest().authenticated()
                );

        // Le decimos a Spring que use nuestro 'authenticationProvider'
        http.authenticationProvider(authenticationProvider());

        // Le decimos a Spring que añada nuestro "guardia" (AuthTokenFilter)
        // ANTES del filtro normal de username/password.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
package com.turnofacil.salta.config;

import com.turnofacil.salta.security.AuthEntryPointJwt;
import com.turnofacil.salta.security.AuthTokenFilter;
import com.turnofacil.salta.service.impl.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@EnableWebSecurity
@EnableMethodSecurity // Habilita la seguridad a nivel de método (ej. @PreAuthorize)
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
                // Manejador de errores 401 (No Autorizado)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // Política de sesión SIN ESTADO (Stateless) para que funcione con JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configuración de las rutas
                .authorizeHttpRequests(authz -> authz
                        // 1. Peticiones de Pre-vuelo (CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 2. Rutas Públicas (Login, Registro y Búsquedas)
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/public/**").permitAll()
                        .requestMatchers("/api/v1/type-of-center/**").permitAll()
                        // 3. Rutas de Admin (CRUDs simples)
                        // Permitimos GET públicos, pero POST/PUT/DELETE solo para ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/v1/health-centers/**").permitAll()
                        .requestMatchers("/api/v1/health-centers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/specialities/**").permitAll()
                        .requestMatchers("/api/v1/specialities/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/schedules/**").hasAnyRole("ADMIN", "PROFESIONAL")
                        .requestMatchers("/api/v1/speciality-details/**").hasRole("ADMIN")
                        // 4. Rutas Protegidas por @PreAuthorize (en los controladores)
                        // Solo requerimos que estén autenticados.
                        // @PreAuthorize se encargará de verificar el ROL (ADMIN o PROFESSIONAL)
                        .requestMatchers("/api/v1/users/**").authenticated()
                        .requestMatchers("/api/v1/professionals/**").authenticated()
                        // 5. Rutas de Paciente y Perfil
                        .requestMatchers("/api/v1/appointments/**").authenticated()
                        .requestMatchers("/api/v1/profile/**").authenticated()
                        // 6. Todas las demás rutas
                        .anyRequest().authenticated()
                );

        // Define el proveedor de autenticación
        http.authenticationProvider(authenticationProvider());

        // Añade nuestro filtro de tokens JWT antes del filtro de login estándar
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
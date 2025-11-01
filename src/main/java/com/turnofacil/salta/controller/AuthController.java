package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.auth.AuthResponseDTO;
import com.turnofacil.salta.dto.auth.LoginRequestDTO;
import com.turnofacil.salta.dto.auth.RegisterRequestDTO;
import com.turnofacil.salta.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth") //ruta para registrarse o registrar si lo hace un admin
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(
        @Valid @RequestBody RegisterRequestDTO registerRequestDTO
    ){
        MessageResponse response = authService.register(registerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //falta el login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ){
        AuthResponseDTO responseDTO = authService.login(loginRequestDTO);
        // Devolvemos 200 OK y el DTO con el token
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}

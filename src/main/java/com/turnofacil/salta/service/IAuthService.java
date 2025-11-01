package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.auth.AuthResponseDTO;
import com.turnofacil.salta.dto.auth.LoginRequestDTO;
import com.turnofacil.salta.dto.auth.RegisterRequestDTO;

public interface IAuthService {
    MessageResponse register(RegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);
}

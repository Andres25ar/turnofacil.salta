package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.entity.User;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //buscamos en la base de datos por username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con email: " + username));
        //casteamos a UserDetails para que lo entienda spring
        return UserDetailsImpl.build(user);
    }
}

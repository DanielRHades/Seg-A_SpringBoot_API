package com.proj.SegAProj.services;

import com.proj.SegAProj.models.AuthenticationRequest;
import com.proj.SegAProj.models.AuthenticationResponse;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.jwtService =jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getIdUni(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByIdUni(request.getIdUni())
                .orElseThrow(()-> new RuntimeException("No existe el usuario."));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

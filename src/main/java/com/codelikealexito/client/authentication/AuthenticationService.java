package com.codelikealexito.client.authentication;

import com.codelikealexito.client.client.ScientistService;
import com.codelikealexito.client.dto.ScientistDto;
import com.codelikealexito.client.dto.ValidateTokenDto;
import com.codelikealexito.client.entities.Role;
import com.codelikealexito.client.entities.Scientist;
import com.codelikealexito.client.exceptions.CustomResponseStatusException;
import com.codelikealexito.client.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;
    private final ScientistService scientistService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtTokenUtil, ScientistService scientistService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.scientistService = scientistService;
    }

    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(AuthenticationRequest authenticationRequest) throws CustomResponseStatusException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e ) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "ERR_CODE", "Bad credentials");
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final Scientist scientist = scientistService.getClientByUsername(userDetails.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final Date expirationTime = jwtTokenUtil.extractExpiration(jwt);
        List<Role> roles = (List<Role>) scientist.getRoles();
        return ResponseEntity.ok(new AuthenticationResponse(scientist.getId(), scientist.getUsername(),jwt, expirationTime, roles));
    }

    public ResponseEntity<ValidateTokenDto> validateJwtToken(String token) {
        String extractedToken = (token.startsWith("Bearer ")) ? token.substring(7) : token;
        String username = jwtTokenUtil.extractUserName(extractedToken);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        if (userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
            throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR_CODE", "User not found");
        }
        final Scientist scientist = scientistService.getClientByUsername(userDetails.getUsername());
        boolean isValid = jwtTokenUtil.validateToken(extractedToken, userDetails);
        ScientistDto scientistDto = new ScientistDto();
        scientistDto.setId(scientist.getId());
        scientistDto.setUsername(scientist.getUsername());
        scientistDto.setToken(token);

        ValidateTokenDto validateTokenDto = new ValidateTokenDto();
        validateTokenDto.setScientistDto(scientistDto);
        validateTokenDto.setIsValid(isValid);

        return ResponseEntity.ok(validateTokenDto);
    }


}

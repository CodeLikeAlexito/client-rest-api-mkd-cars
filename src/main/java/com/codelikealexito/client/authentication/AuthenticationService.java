package com.codelikealexito.client.authentication;

import com.codelikealexito.client.client.ClientService;
import com.codelikealexito.client.dto.ClientDto;
import com.codelikealexito.client.dto.ValidateTokenDto;
import com.codelikealexito.client.entities.Client;
import com.codelikealexito.client.exceptions.CustomResponseStatusException;
import com.codelikealexito.client.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtTokenUtil;
    private final ClientService clientService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtTokenUtil, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.clientService = clientService;
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
        final Client client = clientService.getClientByUsername(userDetails.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final Date expirationTime = jwtTokenUtil.extractExpiration(jwt);
        return ResponseEntity.ok(new AuthenticationResponse(client.getId(), client.getUsername(),jwt, expirationTime));
    }

    public ResponseEntity<ValidateTokenDto> validateJwtToken(String token) {
        String extractedToken = (token.startsWith("Bearer ")) ? token.substring(7) : token;
        String username = jwtTokenUtil.extractUserName(extractedToken);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);
        if (userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
            throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR_CODE", "User not found");
        }
        final Client client = clientService.getClientByUsername(userDetails.getUsername());
        boolean isValid = jwtTokenUtil.validateToken(extractedToken, userDetails);
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setUsername(client.getUsername());
        clientDto.setToken(token);

        ValidateTokenDto validateTokenDto = new ValidateTokenDto();
        validateTokenDto.setClientDto(clientDto);
        validateTokenDto.setIsValid(isValid);

        return ResponseEntity.ok(validateTokenDto);
    }


}

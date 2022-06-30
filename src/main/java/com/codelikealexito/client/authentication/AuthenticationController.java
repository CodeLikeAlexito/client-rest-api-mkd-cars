package com.codelikealexito.client.authentication;

import com.codelikealexito.client.dto.ValidateTokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scientist")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return authenticationService.createAuthenticationToken(authenticationRequest);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ValidateTokenDto> validateJwtToken(@RequestParam String token) {
        return authenticationService.validateJwtToken(token);
    }

}

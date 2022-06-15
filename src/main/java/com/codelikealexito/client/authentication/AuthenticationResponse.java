package com.codelikealexito.client.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private long id;
    private String username;
    private String token;
}

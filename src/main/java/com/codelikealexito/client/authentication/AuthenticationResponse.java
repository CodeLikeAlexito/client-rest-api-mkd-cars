package com.codelikealexito.client.authentication;

import com.codelikealexito.client.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

import java.util.Date;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private long id;
    private String username;
    private String token;
    private Date expirationTime;
    private List<Role> role;
}

package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface ClientService extends UserDetailsService {
    Client register(ClientRegistrationDto userDto);
    Client editClient(ClientRegistrationDto userDto);
    List<Client> getAllClients();
    Client getClientByUsername(String username);

    Map<String, Boolean> deleteUser(Long userId);

    Client getClientById(Long id);

    Client updateClient(Long clientId, ClientUpdateDto clientDetails);
}

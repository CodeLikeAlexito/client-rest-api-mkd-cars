package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Scientist;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ScientistService extends UserDetailsService {
    Scientist register(ScientistRegistrationDto userDto);
    List<Scientist> getAllClients();
    Optional<Scientist> getClientByUsername(String username);

    Map<String, Boolean> deleteUser(Long userId);

    Scientist getClientById(Long id);

    Scientist updateClient(Long clientId, ScientistUpdateDto clientDetails);
}

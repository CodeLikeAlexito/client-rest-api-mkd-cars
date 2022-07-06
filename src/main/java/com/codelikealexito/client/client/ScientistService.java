package com.codelikealexito.client.client;

import com.codelikealexito.client.dto.PasswordResetDto;
import com.codelikealexito.client.entities.Scientist;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface ScientistService extends UserDetailsService {
    Scientist register(ScientistRegistrationDto userDto);
    Scientist editClient(ScientistRegistrationDto userDto);
    List<Scientist> getAllClients();
    Scientist getClientByUsername(String username);

    Map<String, Boolean> deleteUser(Long userId);

    Scientist getClientById(Long id);

    Scientist updateClient(Long clientId, ScientistUpdateDto clientDetails);
    void updateResetPasswordToken(String token, String email);
    Scientist getScientistByResetPasswordToken(String token);
    void resetPassword(String token, PasswordResetDto passwordResetDto);
}

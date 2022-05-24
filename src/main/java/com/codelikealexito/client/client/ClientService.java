package com.codelikealexito.client.client;

import com.codelikealexito.client.VO.ResponseTemplateVO;
import com.codelikealexito.client.entities.Client;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface ClientService extends UserDetailsService {
    Client register(ClientRegistrationDto userDto);
    Client editClient(ClientRegistrationDto userDto);
    List<Client> getAllClients();
    ResponseTemplateVO getClientWithCars(String author);
}

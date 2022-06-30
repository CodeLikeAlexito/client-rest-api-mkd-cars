package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Client;
import com.codelikealexito.client.entities.Role;
import com.codelikealexito.client.enums.Roles;
import com.codelikealexito.client.exceptions.CustomResponseStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsername(username);
        if (client == null) {
            log.error("User not found in the database.");
            throw new UsernameNotFoundException(username);
        }
        log.info("User found in the database: {}", username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        client.getRoles().forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(client.getUsername(), client.getPassword(), authorities);
    }

    @Override
    public Client register(ClientRegistrationDto clientDto) {

        if(emailExists(clientDto.getEmail())){
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "ERR501", "Email that you entered already exists!");
        }

        if(clientExists(clientDto.getUsername())) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "ERR502", "Username that you entered already exists!");
        }

        Client user = Client.createUserWithFullInformation(null, clientDto.getUsername(), clientDto.getFirstName(), clientDto.getLastName(), clientDto.getEmail(),
                passwordEncoder.encode(clientDto.getPassword()), clientDto.getCity(), clientDto.getAddress(),
                clientDto.getPhone(), Arrays.asList(Role.giveRole(Roles.USER.name())));
        return clientRepository.save(user);
    }

    //TODO to be implemented
    @Override
    public Client editClient(ClientRegistrationDto userDto) {
        if(!clientExists(userDto.getUsername())){

        }

        if(!emailExists(userDto.getEmail())) {

        }

        return null;

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR603", "User does not exists!"));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "SOME_ERR_CODE", "User does not exists!"));
    }

    @Override
    public Client updateClient(Long clientId, ClientUpdateDto clientDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomResponseStatusException(HttpStatus.NOT_FOUND, "ERR603", "Client does not exists!"));

        final Client updatedClient = Client.updateScientist(clientId, clientDto.getUsername(), clientDto.getFirstName(), clientDto.getLastName(), clientDto.getEmail(),
                passwordEncoder.encode(client.getPassword()), clientDto.getCity(), clientDto.getAddress(),
                clientDto.getPhone(), clientDto.getRoles());
        //Arrays.asList(Role.giveRole(Roles.USER.name()))

        return clientRepository.save(updatedClient);
    }

    private boolean clientExists(String username) {
        return clientRepository.findByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return clientRepository.findByEmail(email) != null;
    }
}

package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody ClientRegistrationDto clientDto) {
        return ResponseEntity.ok(clientService.register(clientDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable String userName) {
        return ResponseEntity.ok(clientService.loadUserByUsername(userName));
    }

}

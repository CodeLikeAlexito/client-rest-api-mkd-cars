package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<UserDetails> getClientByUsername(@PathVariable String userName) {
        return ResponseEntity.ok(clientService.loadUserByUsername(userName));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") Long userId){
        return ResponseEntity.ok(clientService.deleteUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId,
                                                 @Valid @RequestBody ClientUpdateDto clientDetails){
        return ResponseEntity.ok(clientService.updateInvoice(clientId, clientDetails));
    }

}

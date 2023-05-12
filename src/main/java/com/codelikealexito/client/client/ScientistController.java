package com.codelikealexito.client.client;

import com.codelikealexito.client.entities.Scientist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/scientist")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ScientistController {

    private final ScientistService scientistService;

    public ScientistController(ScientistService scientistService) {
        this.scientistService = scientistService;
    }

    @PostMapping("/")
    public ResponseEntity<Scientist> registerClient(@RequestBody ScientistRegistrationDto clientDto) {
        return ResponseEntity.ok(scientistService.register(clientDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<Scientist>> getAllClients() {
        return ResponseEntity.ok(scientistService.getAllClients());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDetails> getClientByUsername(@PathVariable String userName) {
        return ResponseEntity.ok(scientistService.loadUserByUsername(userName));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Scientist> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(scientistService.getClientById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") Long userId){
        return ResponseEntity.ok(scientistService.deleteUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Scientist> updateClient(@PathVariable(value = "id") Long clientId,
                                                  @Valid @RequestBody ScientistUpdateDto clientDetails){
        return ResponseEntity.ok(scientistService.updateClient(clientId, clientDetails));
    }

}

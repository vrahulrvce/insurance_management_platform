package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // Fetch all clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Fetch a specific client by ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            return ResponseEntity.ok().body(client.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new client
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    // Update a client's information
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId, 
                                                @RequestBody Client clientDetails) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            Client updatedClient = client.get();
            updatedClient.setName(clientDetails.getName());
            updatedClient.setDateOfBirth(clientDetails.getDateOfBirth());
            updatedClient.setAddress(clientDetails.getAddress());
            updatedClient.setContactInfo(clientDetails.getContactInfo());
            return ResponseEntity.ok(clientRepository.save(updatedClient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a client
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable(value = "id") Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


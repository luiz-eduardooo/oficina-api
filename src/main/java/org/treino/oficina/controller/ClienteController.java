package org.treino.oficina.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.treino.oficina.dtos.cliente.ClienteRequestDTO;
import org.treino.oficina.dtos.cliente.ClienteResponseDTO;
import org.treino.oficina.services.ClienteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody @Valid ClienteRequestDTO dto){
        return ResponseEntity.ok(service.criarCliente(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> verClientePorId(@PathVariable UUID id){
        return ResponseEntity.ok(service.verClientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes(){
        return ResponseEntity.ok(service.verTodosClientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable UUID id){
        service.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}

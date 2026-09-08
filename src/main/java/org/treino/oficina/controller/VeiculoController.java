package org.treino.oficina.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.treino.oficina.dtos.veiculo.VeiculoRequestDTO;
import org.treino.oficina.dtos.veiculo.VeiculoResponseDTO;
import org.treino.oficina.services.VeiculoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
public class VeiculoController {
    private final VeiculoService service;

    @PostMapping("/{idCliente}")
    public ResponseEntity<VeiculoResponseDTO> criarVeiculo(@RequestBody @Valid VeiculoRequestDTO dto, @PathVariable UUID idCliente){
        return ResponseEntity.status(201).body(service.criarVeiculo(dto, idCliente));
    }

    @GetMapping("/{idCliente}/{idVeiculo}")
    public ResponseEntity<VeiculoResponseDTO> verVeiculo(@PathVariable UUID idCliente, @PathVariable Long idVeiculo){
        return ResponseEntity.ok(service.verVeiculo(idCliente, idVeiculo));
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<VeiculoResponseDTO>> verTodosVeiculos(@PathVariable UUID idCliente){
        return ResponseEntity.ok(service.verTodosVeiculos(idCliente));
    }

    @DeleteMapping("/{idCliente}/{idVeiculo}")
    public ResponseEntity<Void> removerVeiculo(@PathVariable UUID idCliente, @PathVariable Long idVeiculo){
        service.removerVeiculo(idCliente, idVeiculo);
        return ResponseEntity.noContent().build();
    }
}

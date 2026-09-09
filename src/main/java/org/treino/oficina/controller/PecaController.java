package org.treino.oficina.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.treino.oficina.dtos.peca.PecaRequestDTO;
import org.treino.oficina.dtos.peca.PecaResponseDTO;
import org.treino.oficina.services.PecaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/peca")
public class PecaController {
    private final PecaService pecaService;

    @PostMapping
    public ResponseEntity<PecaResponseDTO> criarPeca(@RequestBody @Valid PecaRequestDTO dto){
        return ResponseEntity.status(201).body(pecaService.criarPeca(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> verPeca(@PathVariable Long id){
        return ResponseEntity.ok(pecaService.verPeca(id));
    }

    @GetMapping
    public ResponseEntity<List<PecaResponseDTO>> verTodasPecas(){
        return ResponseEntity.ok(pecaService.verTodasPecas());
    }



}

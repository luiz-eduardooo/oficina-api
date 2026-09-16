package org.treino.oficina.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.treino.oficina.dtos.itens.ItemPecaRequestDTO;
import org.treino.oficina.dtos.itens.ItemServicoRequestDTO;
import org.treino.oficina.dtos.ordemServico.OrdemServicoResponseDTO;
import org.treino.oficina.services.OrdemServicoService;

@RestController
@RequestMapping("/os")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @PostMapping("/{idVeiculo}")
    public ResponseEntity<OrdemServicoResponseDTO> criarOs(@PathVariable Long idVeiculo){
        return ResponseEntity.status(201).body(ordemServicoService.criarOs(idVeiculo));
    }

    @PatchMapping("/{idOs}")
    public ResponseEntity<OrdemServicoResponseDTO> fecharOs(@PathVariable Long idOs) {
        return ResponseEntity.ok(ordemServicoService.fecharOs(idOs));
    }

    @PostMapping("/{idOs}/peca")
    public ResponseEntity<OrdemServicoResponseDTO> adicionarItemPeca(@PathVariable Long idOs, @Valid @RequestBody ItemPecaRequestDTO dto){
        return ResponseEntity.status(201).body(ordemServicoService.adicionarItemPeca(idOs, dto));
    }
    @PostMapping("/{idOs}/servico")
    public ResponseEntity<OrdemServicoResponseDTO> adicionarItemServico(@PathVariable Long idOs, @Valid @RequestBody ItemServicoRequestDTO dto){
        return ResponseEntity.status(201).body(ordemServicoService.adicionarItemServico(idOs, dto));
    }

    @DeleteMapping("/{idOs}/{idItem}")
    public ResponseEntity<OrdemServicoResponseDTO> deletarItem(@PathVariable Long idOs, @PathVariable Long idItem){
        return ResponseEntity.status(200).body(ordemServicoService.retirarItemOs(idOs, idItem));
    }
}



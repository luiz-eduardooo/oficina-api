package org.treino.oficina.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.treino.oficina.dtos.peca.PecaRequestDTO;
import org.treino.oficina.dtos.peca.PecaResponseDTO;
import org.treino.oficina.entities.Peca;
import org.treino.oficina.exceptions.PecaNaoEncontradaException;
import org.treino.oficina.repositories.PecaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PecaService {
    private final PecaRepository pecaRepository;

    public PecaResponseDTO criarPeca(PecaRequestDTO dto){
        Peca peca = new Peca(dto.nome(), dto.codigo(), dto.preco(), dto.quantidadeEstoque());
        Peca pecaSalva = pecaRepository.save(peca);
        return toResponseDTO(pecaSalva);
    }


    public PecaResponseDTO verPeca(Long idPeca){
        Peca peca = pecaRepository.findById(idPeca).orElseThrow(()-> new PecaNaoEncontradaException("Essa peça não foi encontrada na base de dados!"));
        return toResponseDTO(peca);
    }

    public List<PecaResponseDTO> verTodasPecas(){
        return pecaRepository.findAll().stream().map(this::toResponseDTO).toList();
    }


    private PecaResponseDTO toResponseDTO(Peca peca){
        return new PecaResponseDTO(peca.getId(), peca.getNome(), peca.getCodigo(), peca.getPreco(), peca.getQuantidadeEstoque());
    }

}

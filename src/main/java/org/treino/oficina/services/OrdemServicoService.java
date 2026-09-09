package org.treino.oficina.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.treino.oficina.dtos.ordemServico.OrdemServicoResponseDTO;
import org.treino.oficina.entities.OrdemServico;
import org.treino.oficina.entities.Veiculo;
import org.treino.oficina.enums.StatusOs;
import org.treino.oficina.exceptions.OrdemServicoJaAbertaException;
import org.treino.oficina.exceptions.VeiculoNaoEncontradoException;
import org.treino.oficina.repositories.OrdemServicoRepository;
import org.treino.oficina.repositories.VeiculoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {
    private OrdemServicoRepository ordemServicoRepository;
    private VeiculoRepository veiculoRepository;

    public OrdemServicoResponseDTO criarOs(Long idVeiculo){
        if(ordemServicoRepository.existsByVeiculoIdAndStatusIn(idVeiculo, List.of(StatusOs.ABERTA, StatusOs.EM_ANDAMENTO))){
            throw new OrdemServicoJaAbertaException("Esse veiculo ja está com uma ordem de serviço aberta!");
        }
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(()-> new VeiculoNaoEncontradoException("Esse veiculo não foi encontrado na base de dados!"));
        OrdemServico ordemServico = new OrdemServico(veiculo);
        OrdemServico ordemServicoSalva = ordemServicoRepository.save(ordemServico);
        return toResponseDTO(ordemServicoSalva);
    }




    private OrdemServicoResponseDTO toResponseDTO(OrdemServico ordemServico){
        return new OrdemServicoResponseDTO(ordemServico.getId(), ordemServico.getStatus(), ordemServico.getDataFechamento(), ordemServico.getValorTotal());
    }
}

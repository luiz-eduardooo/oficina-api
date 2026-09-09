package org.treino.oficina.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.treino.oficina.dtos.itens.ItemPecaRequestDTO;
import org.treino.oficina.dtos.itens.ItemResponseDTO;
import org.treino.oficina.dtos.itens.ItemServicoRequestDTO;
import org.treino.oficina.dtos.ordemServico.OrdemServicoResponseDTO;
import org.treino.oficina.entities.*;
import org.treino.oficina.enums.StatusOs;
import org.treino.oficina.exceptions.*;
import org.treino.oficina.repositories.OrdemServicoRepository;
import org.treino.oficina.repositories.PecaRepository;
import org.treino.oficina.repositories.VeiculoRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {
    private final OrdemServicoRepository ordemServicoRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;

    @Transactional
    public OrdemServicoResponseDTO criarOs(Long idVeiculo){
        if(ordemServicoRepository.existsByVeiculoIdAndStatusIn(idVeiculo, List.of(StatusOs.ABERTA, StatusOs.EM_ANDAMENTO))){
            throw new OrdemServicoJaAbertaException("Esse veiculo ja está com uma ordem de serviço aberta!");
        }
        Veiculo veiculo = veiculoRepository.findById(idVeiculo).orElseThrow(()-> new VeiculoNaoEncontradoException("Esse veiculo não foi encontrado na base de dados!"));
        OrdemServico ordemServico = new OrdemServico(veiculo);
        OrdemServico ordemServicoSalva = ordemServicoRepository.save(ordemServico);
        return toResponseDTO(ordemServicoSalva);
    }

    @Transactional
    public OrdemServicoResponseDTO adicionarItemServico(Long idOs,ItemServicoRequestDTO dto){
        OrdemServico ordemServico = procurarOs(idOs);
        ItemServico itemServico = new ItemServico(dto.valor(), dto.descricao(), dto.horas());
        ordemServico.adicionarItem(itemServico);
        return toResponseDTO(ordemServico);
    }

    @Transactional
    public OrdemServicoResponseDTO adicionarItemPeca(Long idOs, ItemPecaRequestDTO dto){
        OrdemServico ordemServico = procurarOs(idOs);
        Peca peca = pecaRepository.findById(dto.pecaId()).orElseThrow(()-> new PecaNaoEncontradaException("Essa peça não foi encontrada na base de dados!"));
        ItemPeca itemPeca = new ItemPeca(peca, dto.quantidade());
        ordemServico.adicionarItem(itemPeca);
        return toResponseDTO(ordemServico);
    }

    @Transactional
    public OrdemServicoResponseDTO fecharOs(Long idOs){
        OrdemServico ordemServico = procurarOs(idOs);
        validarOs(ordemServico);
        BigDecimal subTotal = calcularValorItensBaixandoEstoqueOs(ordemServico);
        ordemServico.definirDataFechamento(Instant.now());
        ordemServico.adicionarValorTotal(subTotal);
        ordemServico.fecharOrdemServico();
        return toResponseDTO(ordemServico);
    }


    private OrdemServico procurarOs(Long idOs){
        return ordemServicoRepository.findById(idOs).orElseThrow(()-> new OsNaoEncontradaException("Essa ordem de serviço não foi encontrada na base de dados!"));
    }




    private BigDecimal calcularValorItensBaixandoEstoqueOs(OrdemServico ordemServico){
        BigDecimal subTotal = new BigDecimal("0");
        for(Item item : ordemServico.getItems()){
            if(item instanceof ItemPeca itemPeca){
                itemPeca.getPeca().retirarEstoque(itemPeca.getQuantidade());
            }
            subTotal = subTotal.add(item.calcularSubTotal());
        }
        return subTotal;
    }
    private OrdemServicoResponseDTO toResponseDTO(OrdemServico ordemServico){
        return new OrdemServicoResponseDTO(ordemServico.getId(), ordemServico.getStatus(), ordemServico.getDataFechamento(), ordemServico.getValorTotal(),toListItemResponseDTOS(ordemServico.getItems()));
    }

    private List<ItemResponseDTO> toListItemResponseDTOS(List<Item> itens){
        return itens.stream().map(this::toItemResponseDTO).toList();
    }

    private void validarOs(OrdemServico ordemServico){
        if(ordemServico.getStatus() == StatusOs.CANCELADA || ordemServico.getStatus() == StatusOs.FECHADA){
            throw new OrdemDeServicoFinalizadaException("Essa ordem de serviço ja foi finalizada.");
        }
        if(ordemServico.getItems().size() <= 0){
            throw new OrdemVaziaException("Essa ordem de serviço não tem nenhum item.");
        }
    }

    private ItemResponseDTO toItemResponseDTO(Item item){
        if(item instanceof ItemServico itemServico){
            return new ItemResponseDTO(itemServico.getId(), itemServico.getValorUnitario(), "SERVICO", itemServico.getHoras(), itemServico.getDescricao(), null);
        }else if(item instanceof ItemPeca itemPeca){
            return new ItemResponseDTO(itemPeca.getId(), itemPeca.getValorUnitario(), "PECA", null, null, itemPeca.getQuantidade());
        }
        return null;
    }
}

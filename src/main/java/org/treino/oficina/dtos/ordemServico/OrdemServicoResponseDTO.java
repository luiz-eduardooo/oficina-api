package org.treino.oficina.dtos.ordemServico;

import org.treino.oficina.dtos.itens.ItemResponseDTO;
import org.treino.oficina.enums.StatusOs;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrdemServicoResponseDTO(Long id, StatusOs status, Instant dataFechamento, BigDecimal valorTotal, List<ItemResponseDTO> itens) {
}

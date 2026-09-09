package org.treino.oficina.dtos.ordemServico;

import org.treino.oficina.enums.StatusOs;

import java.math.BigDecimal;
import java.time.Instant;

public record OrdemServicoResponseDTO(Long id, StatusOs status, Instant dataFechamento, BigDecimal valorTotal) {
}

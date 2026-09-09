package org.treino.oficina.dtos.itens;

import java.math.BigDecimal;

public record ItemResponseDTO(Long id, BigDecimal valorUnitario, String tipo, BigDecimal horas, String descricao, Integer quantidade) {
}

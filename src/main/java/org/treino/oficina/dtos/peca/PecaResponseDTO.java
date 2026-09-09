package org.treino.oficina.dtos.peca;

import java.math.BigDecimal;

public record PecaResponseDTO(Long id, String nome, String codigo, BigDecimal preco, int quantidadeEstoque) {
}

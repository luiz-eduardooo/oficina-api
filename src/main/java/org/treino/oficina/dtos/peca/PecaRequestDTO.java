package org.treino.oficina.dtos.peca;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PecaRequestDTO(@NotBlank String nome, @NotBlank String codigo, @NotNull @Positive BigDecimal preco,@Min(0) int quantidadeEstoque) {
}

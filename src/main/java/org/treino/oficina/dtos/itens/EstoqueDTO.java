package org.treino.oficina.dtos.itens;

import jakarta.validation.constraints.PositiveOrZero;

public record EstoqueDTO(@PositiveOrZero int quantidade) {
}

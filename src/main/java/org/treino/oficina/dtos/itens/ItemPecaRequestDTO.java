package org.treino.oficina.dtos.itens;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ItemPecaRequestDTO(@NotNull Long pecaId, @NotNull @Positive Integer quantidade) { }

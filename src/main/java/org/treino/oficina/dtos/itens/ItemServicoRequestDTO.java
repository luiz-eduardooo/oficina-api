package org.treino.oficina.dtos.itens;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemServicoRequestDTO(@NotBlank String descricao, @NotNull @Positive BigDecimal valor,@NotNull @Positive BigDecimal horas) { }
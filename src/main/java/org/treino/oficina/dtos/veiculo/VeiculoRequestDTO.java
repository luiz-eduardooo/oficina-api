package org.treino.oficina.dtos.veiculo;

import jakarta.validation.constraints.NotBlank;

public record VeiculoRequestDTO(@NotBlank String placa) {
}

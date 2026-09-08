package org.treino.oficina.dtos.cliente;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(@NotBlank String nome, @NotBlank @CPF String cpf) {
}

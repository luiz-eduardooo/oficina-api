package org.treino.oficina.dtos.cliente;

import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome, String cpf) {
}

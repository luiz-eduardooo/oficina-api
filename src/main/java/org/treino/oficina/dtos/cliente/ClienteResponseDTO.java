package org.treino.oficina.dtos.cliente;

import org.treino.oficina.entities.Veiculo;

import java.util.List;
import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome, String cpf) {
}

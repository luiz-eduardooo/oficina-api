package org.treino.oficina.dtos.veiculo;

import org.treino.oficina.dtos.cliente.ClienteResponseDTO;

public record VeiculoResponseDTO(Long id, String placa, ClienteResponseDTO cliente) {
}

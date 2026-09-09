package org.treino.oficina.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treino.oficina.entities.Cliente;
import org.treino.oficina.entities.Veiculo;

import java.util.List;
import java.util.UUID;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByPlaca(String placa);

    List<Veiculo> findAllByClienteId(UUID idCliente);

    Long id(Long id);
}

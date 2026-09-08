package org.treino.oficina.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treino.oficina.entities.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}

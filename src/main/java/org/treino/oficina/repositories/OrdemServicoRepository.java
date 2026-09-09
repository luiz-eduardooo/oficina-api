package org.treino.oficina.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.treino.oficina.entities.OrdemServico;
import org.treino.oficina.enums.StatusOs;

import java.util.Collection;
import java.util.Optional;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    boolean existsByVeiculoIdAndStatusIn(Long idVeiculo, Collection<StatusOs> status);

    @EntityGraph(attributePaths = {"items", "items.peca"})
    Optional<OrdemServico> findWithItensById(Long id);
}

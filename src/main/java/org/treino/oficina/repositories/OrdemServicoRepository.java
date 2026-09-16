package org.treino.oficina.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.treino.oficina.entities.OrdemServico;
import org.treino.oficina.enums.StatusOs;

import java.util.Collection;
import java.util.Optional;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    boolean existsByVeiculoIdAndStatusIn(Long idVeiculo, Collection<StatusOs> status);

    @Query("""
       select distinct os from OrdemServico os
       left join fetch os.items i
       left join fetch ItemPeca ip on ip = i
       left join fetch ip.peca
       where os.id = :id
       """)
    Optional<OrdemServico> findWithItensById(@Param("id") Long id);
}

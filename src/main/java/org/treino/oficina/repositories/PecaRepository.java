package org.treino.oficina.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treino.oficina.entities.Peca;

public interface PecaRepository extends JpaRepository<Peca, Long> {
}

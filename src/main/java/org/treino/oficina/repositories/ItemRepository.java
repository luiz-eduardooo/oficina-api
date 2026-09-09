package org.treino.oficina.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.treino.oficina.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

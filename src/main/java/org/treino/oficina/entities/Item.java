package org.treino.oficina.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ordem", nullable = false)
    private OrdemServico ordemServico;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    public void atribuirOrdemServico(OrdemServico ordemServico){
        this.ordemServico = ordemServico;
    }

    public abstract BigDecimal calcularSubTotal();

    protected Item(BigDecimal valorUnitario){
        this.valorUnitario = valorUnitario;
    }
}

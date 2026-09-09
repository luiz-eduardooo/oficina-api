package org.treino.oficina.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PECA")
public class ItemPeca extends Item {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_peca", nullable = false)
    private Peca peca;

    private int quantidade;



    public ItemPeca(Peca peca, int quantidade) {
        super(peca.getPreco());
        this.peca = peca;
        this.quantidade = quantidade;
    }

    @Override
    public BigDecimal calcularSubTotal() {
        return this.getValorUnitario().multiply(BigDecimal.valueOf(quantidade));
    }
}

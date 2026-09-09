package org.treino.oficina.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SERVICO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ItemServico extends Item{

    @Column
    private String descricao;

    @Column
    private BigDecimal horas;


    public ItemServico(BigDecimal valorUnitario, String descricao, BigDecimal horas) {
        super(valorUnitario);
        this.descricao = descricao;
        this.horas = horas;
    }

    @Override
    public BigDecimal calcularSubTotal() {
        return this.getValorUnitario();
    }
}

package org.treino.oficina.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.treino.oficina.enums.StatusOs;
import org.treino.oficina.exceptions.OrdemDeServicoFinalizadaException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordem_servico")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_veiculo", nullable = false)
    private Veiculo veiculo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOs status = StatusOs.ABERTA;

    @Column
    private Instant dataFechamento;

    @Column(nullable = false)
    private BigDecimal valorTotal = new BigDecimal("0");

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordemServico", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    public OrdemServico(Veiculo veiculo) {
        this.veiculo = veiculo;
        veiculo.adicionarOrdemServico(this);
    }

    public void adicionarItem(Item item){
        if(this.status == StatusOs.FECHADA || this.status == StatusOs.CANCELADA){
            throw new OrdemDeServicoFinalizadaException("Essa ordem de serviço ja foi fechada ou cancelada.");
        }
        this.items.add(item);
        item.atribuirOrdemServico(this);
    }


}

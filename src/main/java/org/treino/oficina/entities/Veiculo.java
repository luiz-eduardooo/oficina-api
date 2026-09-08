package org.treino.oficina.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculo")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "veiculo")
    private final List<OrdemServico> ordemServicoList = new ArrayList<>();

    public Veiculo(String placa){
        this.placa = placa;
    }

    public void atribuirAoCliente(Cliente cliente){
        this.cliente = cliente;
        cliente.adicionarVeiculos(this);
    }

    public void adicionarOrdemServico(OrdemServico ordemServico){
        this.ordemServicoList.add(ordemServico);
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Veiculo veiculo)) return false;
        return id != null && id.equals(veiculo.getId());
    }

    @Override
    public int hashCode() {
        return Veiculo.class.hashCode();
    }
}

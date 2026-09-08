package org.treino.oficina.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Veiculo(String placa){
        this.placa = placa;
    }

    public void atribuirAoCliente(Cliente cliente){
        this.cliente = cliente;
        cliente.adicionarVeiculos(this);
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

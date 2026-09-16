package org.treino.oficina.exceptions.veiculo;

public class VeiculoNaoPertencenteException extends RuntimeException {
    public VeiculoNaoPertencenteException(String message) {
        super(message);
    }
}

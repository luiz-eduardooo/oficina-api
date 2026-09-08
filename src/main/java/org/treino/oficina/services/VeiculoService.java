package org.treino.oficina.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.treino.oficina.dtos.veiculo.VeiculoRequestDTO;
import org.treino.oficina.dtos.veiculo.VeiculoResponseDTO;
import org.treino.oficina.entities.Cliente;
import org.treino.oficina.entities.Veiculo;
import org.treino.oficina.exceptions.ClienteNaoEncontradoException;
import org.treino.oficina.exceptions.PlacaJaExistenteException;
import org.treino.oficina.exceptions.VeiculoNaoEncontradoException;
import org.treino.oficina.exceptions.VeiculoNaoPertencenteException;
import org.treino.oficina.repositories.ClienteRepository;
import org.treino.oficina.repositories.VeiculoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;


    @Transactional
    public VeiculoResponseDTO criarVeiculo(VeiculoRequestDTO dto, UUID idCliente){
        if(veiculoRepository.existsByPlaca(dto.placa())){
            throw new PlacaJaExistenteException("Essa placa ja foi cadastrada na base de dados!");
        }
        Veiculo veiculo = new Veiculo(dto.placa());
        Cliente cliente = procurarCliente(idCliente);
        veiculo.atribuirAoCliente(cliente);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
        return toResponseDTO(veiculoSalvo);
    }


    @Transactional(readOnly = true)
    public VeiculoResponseDTO verVeiculo(UUID idCliente, Long idVeiculo){
        Veiculo veiculo = procurarVeiculo(idVeiculo);
        validarVeiculo(idCliente, veiculo);
        return toResponseDTO(veiculo);
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> verTodosVeiculos(UUID idCliente){
        return veiculoRepository.findAllByClienteId(idCliente).stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public void removerVeiculo(UUID idCliente, Long idVeiculo){

        Veiculo veiculo = procurarVeiculo(idVeiculo);
        validarVeiculo(idCliente, veiculo);
        veiculo.getCliente().removerVeiculo(veiculo);
        veiculoRepository.delete(veiculo);
    }

    private Cliente procurarCliente(UUID id){
        return clienteRepository.findById(id).orElseThrow(()-> new ClienteNaoEncontradoException("Esse cliente não foi encontrado na base de dados!"));
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo veiculo){
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getPlaca());
    }


    private Veiculo procurarVeiculo(Long id){
        return veiculoRepository.findById(id).orElseThrow(()-> new VeiculoNaoEncontradoException("Esse veiculo não foi encontrado na base de dados!"));
    }

    private void validarVeiculo(UUID idCliente, Veiculo veiculo){
        if(!veiculo.getCliente().getId().equals(idCliente)){
            throw new VeiculoNaoPertencenteException("Esse veiculo pertence a outra pessoa.");
        }
    }

}

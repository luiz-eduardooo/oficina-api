package org.treino.oficina.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.treino.oficina.dtos.cliente.ClienteRequestDTO;
import org.treino.oficina.dtos.cliente.ClienteResponseDTO;
import org.treino.oficina.entities.Cliente;
import org.treino.oficina.exceptions.ClienteNaoEncontradoException;
import org.treino.oficina.repositories.ClienteRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDTO criarCliente(ClienteRequestDTO dto){
        Cliente cliente = new Cliente(dto.nome(), dto.cpf());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponseDTO(clienteSalvo);
    }


    @Transactional(readOnly = true)
    public ClienteResponseDTO verClientPorId(UUID id){
        Cliente cliente = procurarCliente(id);
        return toResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> verTodosClientes(){
        return clienteRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public void deletarCliente(UUID id){
        Cliente cliente = procurarCliente(id);
        clienteRepository.delete(cliente);
    }


    private Cliente procurarCliente(UUID id){
        return clienteRepository.findById(id).orElseThrow(()-> new ClienteNaoEncontradoException("Esse cliente não foi encontrado na base de dados!"));
    }
    private ClienteResponseDTO toResponseDTO(Cliente cliente){
        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getCpf());
    }
}

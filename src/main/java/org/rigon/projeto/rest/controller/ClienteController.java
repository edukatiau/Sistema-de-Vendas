package org.rigon.projeto.rest.controller;

import org.rigon.projeto.domain.entity.Cliente;
import org.rigon.projeto.domain.repository.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    //Buscar cliente por ID
    @GetMapping("{id}")
    public Cliente getClienteByID(@PathVariable Integer id){
        return clienteRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.")
                );
    }

    //Cadastrar cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    //Deletar cliente por ID
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Integer id){
        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    //Atualizar cliente por ID
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable Integer id,
                              @RequestBody Cliente cliente){

        clienteRepository.findById(id)
                        .map(clienteExistente -> {
                            cliente.setId(clienteExistente.getId());
                            clienteRepository.save(cliente);
                            return clienteExistente;
                        }).orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado."));
/*
        Esta é a forma que eu faria

        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NO_CONTENT, "Cliente não encontrado."));
        cliente.setId(clienteExistente.getId());
        clienteRepository.save(cliente);
*/

    }

    //Buscar cliente por parâmetro
    @GetMapping
    public List<Cliente> findCliente(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }
}

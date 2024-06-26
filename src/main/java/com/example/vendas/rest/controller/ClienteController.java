package com.example.vendas.rest.controller;

import com.example.vendas.domain.entity.Cliente;
import com.example.vendas.domain.repository.Clientes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private Clientes clientesRepository;

    public ClienteController(Clientes clientes){
        this.clientesRepository = clientes;
    }

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ){
        //pathVariable recebe um parâmetro via url
        return clientesRepository
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientesRepository.findById(id)
                .map(cliente -> {
                    clientesRepository.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));
    }

    @PutMapping("{id}") //quando for atualizar um recurso no servidor, é necessário passar todas as info do cliente atualizada, se n ele faz um update em todos os outros campos setando pra null
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        clientesRepository.findById(id)
                    .map(clienteExistente -> {
                        cliente.setId(clienteExistente.getId());
                        clientesRepository.save(cliente);
                    return clienteExistente;
         }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")); //supplier
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro, Authentication authentication){
        //metodo que permite buscar por todos os dados da base da table especifico bem como filtrar por qualquer coluna que contenha  na
        //base de dados, ignorando letras maiuscula/minuscula
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher); //pegar as propriedas populadas e criar o objeto
        return clientesRepository.findAll(example);
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicRoute(Authentication authentication){
        return ResponseEntity.ok("Public Route OK. Usuário conectado: " + authentication.getName());
    }

    @GetMapping("/private")
    public ResponseEntity<String> privateRoute(){
        return ResponseEntity.ok("Private Route OK");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminRoute(){
        return ResponseEntity.ok("Admin Route OK");
    }
}

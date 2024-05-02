package com.example.vendas.rest.controller;

import com.example.vendas.domain.entity.Produto;
import com.example.vendas.domain.repository.Produtos;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtosRepository;

    public ProdutoController(Produtos produtos){
        this.produtosRepository = produtos;
    }

    @GetMapping("{id}")
    public Produto getProdutoId(@PathVariable Integer id){
        return produtosRepository.findById(id)
                .orElseThrow( () ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@RequestBody @Valid Produto produto){
        return produtosRepository.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id){
        produtosRepository.findById(id)
                .map( produto -> {
                    produtosRepository.delete(produto);
                    return Void.TYPE;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody Produto produto){
        produtosRepository.findById(id)
                .map( produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtosRepository.save(produto);
                    return produtoExistente;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> findProdutos (Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);

    }
}


package com.example.vendas.repositorio;

import com.example.vendas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {

    //convencao utilizada buscando pela propriedade nome seguido da função sql a ser utilizada em
    //tempo de execução - QUERYS METHODS
    @Query(value=" select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Modifying //para todos querys methods que façam alteração nas tabelas, é necessário usar essa anotação.
    void deleteByNome(String nome);
    boolean existsByNome(String nome);

}

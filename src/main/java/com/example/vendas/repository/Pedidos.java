package com.example.vendas.repository;

import com.example.vendas.entity.Cliente;
import com.example.vendas.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);
}

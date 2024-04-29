package com.example.vendas.service;

import com.example.vendas.dto.PedidoDTO;
import com.example.vendas.entity.Pedido;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}

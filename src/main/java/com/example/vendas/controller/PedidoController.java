package com.example.vendas.controller;

import com.example.vendas.dto.PedidoDTO;
import com.example.vendas.entity.Pedido;
import com.example.vendas.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    //concentra todo a regra de negócio no service
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();

    }

}

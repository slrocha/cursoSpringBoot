package com.example.vendas.rest.controller;

import com.example.vendas.domain.entity.ItemPedido;
import com.example.vendas.domain.enums.StatusPedido;
import com.example.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.vendas.rest.dto.InformacoesItemPedidoDTO;
import com.example.vendas.rest.dto.InformacoesPedidoDTO;
import com.example.vendas.rest.dto.PedidoDTO;
import com.example.vendas.domain.entity.Pedido;
import com.example.vendas.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    //concentra toda a regra de negócio no service
    //ctrl + f9 atualiza o projeto sem precisar para a app, isso com o devtools
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

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

    }

    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatusPedido().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converter (List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                itemPedido -> InformacoesItemPedidoDTO.builder()
                        .descricaoProduto(itemPedido.getProduto().getDescricao())
                        .precoUnitario(itemPedido.getProduto().getPreco())
                        .quantidade(itemPedido.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id , @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizarStatus(id, StatusPedido.valueOf(novoStatus));
    }
}

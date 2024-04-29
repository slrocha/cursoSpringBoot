package com.example.vendas.service.impl;

import com.example.vendas.dto.ItemPedidoDTO;
import com.example.vendas.dto.PedidoDTO;
import com.example.vendas.entity.Pedido;
import com.example.vendas.entity.Cliente;
import com.example.vendas.entity.ItemPedido;
import com.example.vendas.entity.Produto;
import com.example.vendas.exception.RegraNegocioException;
import com.example.vendas.repository.Clientes;
import com.example.vendas.repository.ItensPedido;
import com.example.vendas.repository.Pedidos;
import com.example.vendas.repository.Produtos;
import com.example.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //cria construtores com todos atributos obrigatórios . obs: atributos declarados como final
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos pedidoRepository;
    private final Clientes clienteRepositoy;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;

    @Override
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clienteRepositoy.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItens(pedido, dto.getItens());
        pedidoRepository.save(pedido);
        itensPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> dtoItens){
        if(dtoItens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return dtoItens
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}

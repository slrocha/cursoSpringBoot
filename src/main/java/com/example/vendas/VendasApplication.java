package com.example.vendas;

import com.example.vendas.entity.Cliente;
import com.example.vendas.entity.Pedido;
import com.example.vendas.repository.Clientes;
import com.example.vendas.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes, @Autowired Pedidos pedidos){
		return args -> {

			System.out.println("Criando clientes");
			Cliente cliente = new Cliente("Stephanie Lima Rocha");
			clientes.save(cliente);

			Pedido p = new Pedido();
			p.setCliente(cliente);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));
			pedidos.save(p);

			/*Cliente c = clientes.findClienteFetchPedido(cliente.getId());
			System.out.println(c);
			System.out.println(cliente.getPedidos());*/

			pedidos.findByCliente(cliente).forEach(System.out::println);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

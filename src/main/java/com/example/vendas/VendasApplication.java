package com.example.vendas;

import com.example.vendas.entity.Cliente;
import com.example.vendas.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes){
		return args -> {

			System.out.println("Criando clientes");
			clientes.salvar(new Cliente("Stephanie Lima Rocha"));
			clientes.salvar(new Cliente("Mariana Valadãp"));

			System.out.println("BUcando todos clientes");
			List<Cliente> todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);

			todosClientes.forEach(c -> {
				c.setNome(c.getNome() + "atualizada");
				clientes.atualizar(c);
			});

			todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);

			System.out.println("BUscando clientes usando filtro");
			clientes.buscarPorNome("Cli").forEach(System.out::println);

			System.out.println("Deletando clientes");

			clientes.obterTodos().forEach(c -> {
				clientes.deletar(c);
			});

			todosClientes = clientes.obterTodos();
			if(todosClientes.isEmpty()){
				System.out.println("Não há clientes cadastrados");
			}else {
				todosClientes.forEach(System.out::println);
			}
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

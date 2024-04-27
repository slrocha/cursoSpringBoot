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
			clientes.save(new Cliente("Stephanie Lima Rocha"));
			clientes.save(new Cliente("Mariana Valadãp"));

			List<Cliente> result = clientes.encontrarPorNome("Stephanie Lima Rocha");
			result.forEach(System.out::println);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

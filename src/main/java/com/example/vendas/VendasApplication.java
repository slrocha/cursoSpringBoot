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
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
}

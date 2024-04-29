package com.example.vendas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "cpf", length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy ="cliente", fetch = FetchType.LAZY) //lazy otimiza a busca
    private Set<Pedido> pedidos;

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}

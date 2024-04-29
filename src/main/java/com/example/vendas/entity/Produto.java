package com.example.vendas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "preco_unitario")
    private BigDecimal preco;

}

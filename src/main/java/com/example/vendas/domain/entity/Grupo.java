package com.example.vendas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.Id;
@Entity
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;


}


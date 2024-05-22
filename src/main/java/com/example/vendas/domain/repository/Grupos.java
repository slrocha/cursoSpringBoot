package com.example.vendas.domain.repository;

import com.example.vendas.domain.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Grupos extends JpaRepository<Grupo, String> {
    Optional<Grupo> findByNome(String nome);
}

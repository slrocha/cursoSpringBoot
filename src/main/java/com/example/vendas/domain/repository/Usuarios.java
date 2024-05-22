package com.example.vendas.domain.repository;

import com.example.vendas.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByLogin(String login);
}

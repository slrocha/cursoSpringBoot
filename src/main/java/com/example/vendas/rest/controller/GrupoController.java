package com.example.vendas.rest.controller;

import com.example.vendas.domain.entity.Grupo;
import com.example.vendas.domain.repository.Grupos;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final Grupos gruposRepository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Grupo> salvar(@RequestBody Grupo grupo){
        gruposRepository.save(grupo);
        return ResponseEntity.ok(grupo);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Grupo>> listar(){
        return ResponseEntity.ok(gruposRepository.findAll());
    }
}

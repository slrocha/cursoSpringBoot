package com.example.vendas.rest.controller;

import com.example.vendas.domain.entity.Usuario;
import com.example.vendas.domain.repository.Usuarios;
import com.example.vendas.rest.dto.CadastroUsuarioDTO;
import com.example.vendas.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> salvar(@RequestBody CadastroUsuarioDTO body){
        Usuario ususarioSalvo = usuarioService.salvar(body.getUsuario(), body.getPermissoes());
        return ResponseEntity.ok(ususarioSalvo);
    }
}

package com.example.vendas.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rh")
public class RhController {

    @GetMapping("/tecnico")
    @PreAuthorize("hasAnyRole('TECNICO_RH','GERENTE_RH', 'ADMIN')")
    public ResponseEntity<String> techRoute(){
        return ResponseEntity.ok("Tecnico Route OK");
    }

    @GetMapping("/gerente")
    @PreAuthorize("hasAnyRole('GERENTE_RH', 'ADMIN')")
    public ResponseEntity<String> gerenteRoute(){
        return ResponseEntity.ok("Gerente Route OK");
    }

}

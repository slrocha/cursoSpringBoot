package com.example.vendas.service;

import com.example.vendas.domain.entity.Grupo;
import com.example.vendas.domain.entity.Usuario;
import com.example.vendas.domain.entity.UsuarioGrupo;
import com.example.vendas.domain.repository.Grupos;
import com.example.vendas.domain.repository.UsuarioGrupos;
import com.example.vendas.domain.repository.Usuarios;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final Usuarios usuarioRepository;
    private final Grupos grupoRepository;
    private final UsuarioGrupos usuarioGrupoRepository;
    private final PasswordEncoder passwordEncoder;

   @Transactional
    public Usuario salvar(Usuario usuario, List<String> grupos){
       String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
       usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
        List<UsuarioGrupo> listaUsuarioGrupo = grupos.stream().map(nomeGrupo -> {
            Optional<Grupo> possivelNomeGrupo = grupoRepository.findByNome(nomeGrupo);
            if(possivelNomeGrupo.isPresent()){
                Grupo grupo = possivelNomeGrupo.get();
                return new UsuarioGrupo(usuario, grupo);
            }
            return null;
        }).filter(grupo -> grupo != null).collect(Collectors.toList());

        usuarioGrupoRepository.saveAll(listaUsuarioGrupo);
        return usuario;
    }

    public Usuario obterUsuarioComPermissao(String login){
       Optional<Usuario> usuarioLogin = usuarioRepository.findByLogin(login);
       if(usuarioLogin.isEmpty()){
           return null;
       }
       Usuario usuario = usuarioLogin.get();
       List<String> permissoes = usuarioGrupoRepository.findPermissoesByUsuario(usuario);
       usuario.setPermissoes(permissoes);

       return usuario;
    }
}

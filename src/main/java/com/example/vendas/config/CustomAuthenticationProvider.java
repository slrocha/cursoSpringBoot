package com.example.vendas.config;

import com.example.vendas.domain.entity.Usuario;
import com.example.vendas.domain.security.CustomAuthentication;
import com.example.vendas.domain.security.IdentificacaoUsuario;
import com.example.vendas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = (String) authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioComPermissao(login);
        if (usuario != null){
            boolean matches = passwordEncoder.matches(senha, usuario.getSenha());
            if(matches){
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                        usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getPermissoes()
                );
                return new CustomAuthentication(identificacaoUsuario);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

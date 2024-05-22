package com.example.vendas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    //role -> grupo de usuario ou perfil de usuário => master, gerente, frente de loja
    //authority -> permissões de acesso => cadastrar usuários, acessar tela de relatório

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   SenhaMasterAuthenticationProvider senhaMasterAuthenticationProvider,
                                                   CustomAuthenticationProvider customAuthenticationProvider,
                                                   CustomFilter customFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/api/clientes").permitAll();
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults()) //form de lgin para autenticar antes de acessar qualquer rota
                .authenticationProvider(senhaMasterAuthenticationProvider)
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails commomUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles( "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(commomUser, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}

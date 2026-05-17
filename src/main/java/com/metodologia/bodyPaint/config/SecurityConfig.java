package com.metodologia.bodyPaint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.metodologia.bodyPaint.feature.models.Cliente;
import com.metodologia.bodyPaint.feature.repositories.ClienteRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                
                .requestMatchers(
                        "/",
                        "/index.html",
                        "/registro.html",
                        "/admin.html",
                        "/producto.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/uploads/**"
                ).permitAll()

                
                .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes").permitAll()

                
                .requestMatchers("/carritos/**").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.POST, "/pedidos").hasRole("CLIENTE")
                .requestMatchers(HttpMethod.GET, "/pedidos/mis-pedidos").hasRole("CLIENTE")

                
                .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/productos/*/imagen").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")

               
                .requestMatchers(HttpMethod.PUT, "/pedidos/*/cancelar").hasRole("VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/pedidos/*/estado").hasRole("VENDEDOR")
                .requestMatchers(HttpMethod.GET, "/pedidos/**").hasAnyRole("VENDEDOR", "ADMIN")

                
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(ClienteRepository clienteRepository) {
        return email -> {
            Cliente cliente = clienteRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(cliente.getEmail())
                    .password(cliente.getPassword())
                    .roles(cliente.getRol().name().replace("ROLE_", ""))
                    .build();
        };
    }
}
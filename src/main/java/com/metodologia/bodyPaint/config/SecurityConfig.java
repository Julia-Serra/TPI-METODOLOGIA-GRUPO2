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

                        // HTML públicos
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/registro.html",
                                "/producto.html")
                        .permitAll()

                        // GET productos público
                        .requestMatchers(HttpMethod.GET, "/productos").permitAll()

                        // POST producto solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN")

                        // POST cliente público (registro)
                        .requestMatchers(HttpMethod.POST, "/clientes").permitAll()

                        // lo demás autenticado
                        .anyRequest().authenticated())
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
                    .authorities(cliente.getRol().name()) 
                    .build();
        };
    }
}

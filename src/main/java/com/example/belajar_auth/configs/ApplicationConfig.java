package com.example.belajar_auth.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.belajar_auth.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AccountRepository accountRepository;
    private final AuthenticationConfiguration authConfig;

    /**
     * Bean untuk load user berdasarkan email.
     * @return UserDetailsService untuk Spring Security.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> accountRepository.findByEmailAndActiveIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("User dengan email " + username + " tidak ditemukan atau tidak aktif."));
    }

    /**
     * Konfigurasi AuthenticationProvider dengan UserDetailsService & PasswordEncoder.
     * @return DaoAuthenticationProvider yang digunakan untuk autentikasi.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean AuthenticationManager yang diperlukan untuk autentikasi user.
     * @return AuthenticationManager dari konfigurasi Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Menggunakan BCrypt sebagai encoder untuk password hashing.
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

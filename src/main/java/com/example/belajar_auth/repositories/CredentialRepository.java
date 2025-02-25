package com.example.belajar_auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.belajar_auth.entities.CredentialAccount;
import com.example.belajar_auth.enums.CredentialTypeEnum;

public interface CredentialRepository extends JpaRepository<CredentialAccount, String> {
    
    Optional<CredentialAccount> findByTypeAndCredential(CredentialTypeEnum type, String credential);
}

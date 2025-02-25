package com.example.belajar_auth.services.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.belajar_auth.dto.request.RequestSignIn;
import com.example.belajar_auth.dto.request.RequestSignUp;
import com.example.belajar_auth.dto.response.ResponseSignIn;
import com.example.belajar_auth.entities.Account;
import com.example.belajar_auth.enums.AccountRoleEnum;
import com.example.belajar_auth.enums.SignUpTypeEnum;
import com.example.belajar_auth.exceptions.BadRequestException;
import com.example.belajar_auth.exceptions.SystemErrorException;
import com.example.belajar_auth.repositories.AccountRepository;
import com.example.belajar_auth.services.AuthService;
import com.example.belajar_auth.services.JwtService;
import com.example.belajar_auth.utils.EntityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String ping() {
        return "PONG";
    }

    @Override
    public String registerUser(RequestSignUp req) {
        System.out.println("=== Registering User ===");
        System.out.println("First Name: " + req.getFirstName());
        System.out.println("Last Name: " + req.getLastName());
        System.out.println("Email: " + req.getEmail());
        System.out.println("Password: " + req.getPassword());
        System.out.println("Phone Number: " + req.getPhoneNumber());

        String encodedPassword = passwordEncoder.encode(req.getPassword());
        System.out.println("Encoded Password: " + encodedPassword);

        boolean checkExistingEmail = accountRepository.existsByEmail(req.getEmail());
        boolean checkExistingPhoneNumber = accountRepository.existsByPhoneNumber(req.getPhoneNumber());

        System.out.println("Email Exists: " + checkExistingEmail);
        System.out.println("Phone Number Exists: " + checkExistingPhoneNumber);

        if (checkExistingEmail) {
            throw new BadRequestException("Email Already Exists");
        }

        if (checkExistingPhoneNumber) {
            throw new BadRequestException("Phone Number Already Exists");
        }

        Account account = Account.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(encodedPassword)
                .phoneNumber(req.getPhoneNumber())
                .role(AccountRoleEnum.USER)
                .signUpType(SignUpTypeEnum.EMAIL)
                .build();

        EntityUtils.created(account, "SYSTEM");

        try {
            accountRepository.save(account);
            System.out.println("Account successfully created!");
            return "Account successfully created";
        } catch (Exception e) {
            System.out.println("Error saving account: " + e.getMessage());
            throw new SystemErrorException(e);
        }
    }

    @Override
    @Transactional
    public ResponseSignIn loginUser(RequestSignIn req) {
        System.out.println("=== User Login Attempt ===");
        System.out.println("Email: " + req.getEmail());

        Optional<Account> account = accountRepository.findAccountQueryByEmail(req.getEmail());
        System.out.println("Account found: " + account.isPresent());

        if (account.isEmpty()) {
            throw new BadRequestException("Sign in failed: Account not found");
        }

        System.out.println("Stored Password: " + account.get().getPassword());
        System.out.println("Entered Password: " + req.getPassword());

        try {
            return buildSignIn(account.get(), req.getPassword());
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            throw new SystemErrorException(e);
        }
    }

    private ResponseSignIn buildSignIn(Account account, String password) {
        try {
            System.out.println("=== Authenticating User ===");
            System.out.println("Email: " + account.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getEmail(), password));

            System.out.println("Authentication successful: " + authentication.isAuthenticated());

            if (!authentication.isAuthenticated()) {
                System.out.println("Authentication failed for user: " + account.getEmail());
                throw new BadRequestException("Sign in failed: Invalid credentials");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtService.generateToken(userDetails);
            System.out.println("JWT generated: " + jwt);

            return ResponseSignIn.builder().accessToken(jwt).build();
        } catch (Exception e) {
            System.out.println("Error in authentication: " + e.getMessage());
            throw new SystemErrorException(e);
        }
    }
}

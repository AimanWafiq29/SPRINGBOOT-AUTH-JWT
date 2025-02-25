package com.example.belajar_auth.services;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public interface JwtService {

 // Metode untuk mengekstrak username (subjek) dari token JWT
 String extractUsername(String token);

 // Metode untuk mengekstrak klaim tertentu dari token JWT menggunakan fungsi resolver
 <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

 // Metode untuk menghasilkan token JWT berdasarkan informasi pengguna
 String generateToken(UserDetails userDetails);

 // Metode untuk menghasilkan token JWT dengan klaim tambahan
 String generateToken(Map<String, Object> extractClaims, UserDetails userDetails);

 // Metode untuk memvalidasi apakah token sesuai dengan user yang diberikan
 boolean isTokenValid(String token, UserDetails userDetails);
}

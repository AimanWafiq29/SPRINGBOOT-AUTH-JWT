package com.example.belajar_auth.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.belajar_auth.constants.AuthConstant;
import com.example.belajar_auth.entities.Account;
import com.example.belajar_auth.exceptions.NotFoundException;
import com.example.belajar_auth.repositories.AccountRepository;
import com.example.belajar_auth.services.JwtService;

import static com.example.belajar_auth.constants.AuthConstant.HEADER_X_WHO;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final AuthConstant authConstant;
    private final AccountRepository accountRepository;

    /**
     * Mengekstrak username (email) dari token JWT.
     *
     * @param token Token JWT yang akan diekstrak.
     * @return Username (email) yang tersimpan di dalam token.
     */
    public String extractUsername(String token) {
        System.out.println("Mengambil username dari token...");
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Mengekstrak klaim tertentu dari token JWT.
     *
     * @param token Token JWT.
     * @param claimsResolver Fungsi untuk mendapatkan klaim tertentu.
     * @return Nilai klaim yang diambil.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("Mengambil klaim spesifik dari token...");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Membuat token JWT untuk user yang terautentikasi.
     *
     * @param userDetails Detail user yang akan dibuatkan tokennya.
     * @return Token JWT yang telah dibuat.
     */
    public String generateToken(UserDetails userDetails) {
        System.out.println("Membuat token untuk user: " + userDetails.getUsername());

        Optional<Account> account = accountRepository.findByEmailAndActiveIsTrue(userDetails.getUsername());
        if (account.isEmpty()) {
            throw new NotFoundException("Akun tidak ditemukan");
        }

        System.out.println("Akun ditemukan: " + account.get().getEmail());

        Map<String, Object> claims = new HashMap<>();
        claims.put(HEADER_X_WHO, account.get().getId());

        return generateToken(claims, userDetails);
    }

    /**
     * Membuat token JWT dengan klaim tambahan.
     *
     * @param extractClaims Klaim tambahan yang akan dimasukkan ke dalam token.
     * @param userDetails Detail user yang akan dibuatkan tokennya.
     * @return Token JWT yang telah dibuat.
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        long expirationTime = authConstant.getEXPIRATION_TIME();
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        System.out.println("Membuat JWT dengan waktu kedaluwarsa: " + expirationDate);

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Memvalidasi token JWT dengan membandingkan username di dalam token dengan user yang diberikan.
     *
     * @param token Token JWT yang akan divalidasi.
     * @param userDetails Detail user untuk validasi.
     * @return True jika token valid, false jika tidak.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        System.out.println("Memvalidasi token untuk user: " + userDetails.getUsername());
        final String username = extractUsername(token);
        boolean isValid = username.equals(userDetails.getUsername());
        System.out.println("Token valid: " + isValid);
        return isValid;
    }

    /**
     * Mengambil kunci rahasia untuk tanda tangan token JWT.
     *
     * @return Kunci yang digunakan untuk menandatangani token.
     */
    private Key getSignInKey() {
        System.out.println("Mengambil kunci tanda tangan...");

        String secret = authConstant.getSecret();
        System.out.println("Kunci rahasia: " + secret);

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        System.out.println("Kunci berhasil didekode!");

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Mengekstrak semua klaim dari token JWT.
     *
     * @param token Token JWT yang akan diekstrak.
     * @return Klaim yang terkandung dalam token.
     */
    private Claims extractAllClaims(String token) {
        System.out.println("Mengambil semua klaim dari token...");
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

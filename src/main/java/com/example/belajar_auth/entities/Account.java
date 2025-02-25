package com.example.belajar_auth.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.belajar_auth.enums.AccountRoleEnum;
import com.example.belajar_auth.enums.SignUpTypeEnum;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "accounts") // Nama tabel menggunakan bentuk plural (konvensi umum)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity implements UserDetails {

    // Kolom untuk menyimpan nama depan pengguna
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Kolom untuk menyimpan nama belakang pengguna
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Kolom untuk menyimpan email pengguna (unik)
    @Column(name = "email", unique = true)
    private String email;

    // Kolom untuk menyimpan kata sandi pengguna (di-hash)
    @Column(name = "password")
    private String password;

    // Kolom untuk menyimpan nomor telepon pengguna (unik dan wajib diisi)
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    // Kolom untuk menyimpan peran pengguna (enum: ADMIN, USER, dll.)
    @Enumerated(EnumType.STRING) // Simpan nilai enum sebagai string di database
    @Column(name = "role")
    private AccountRoleEnum role;

    // Kolom untuk menyimpan tipe pendaftaran pengguna (enum: EMAIL, GOOGLE, FACEBOOK, dll.)
    @Enumerated(EnumType.STRING) // Simpan nilai enum sebagai string di database
    @Column(name = "sign_up_type", nullable = false)
    private SignUpTypeEnum signUpType;

    // Method untuk mendapatkan otoritas (roles) pengguna
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mengubah role pengguna menjadi GrantedAuthority (untuk Spring Security)
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Method untuk mendapatkan username pengguna (dalam hal ini, email)
    @Override
    public String getUsername() {
        return this.email;
    }

    // Method untuk mengecek apakah akun belum kedaluwarsa
    @Override
    public boolean isAccountNonExpired() {
        return true; // Akun tidak pernah kedaluwarsa
    }

    // Method untuk mengecek apakah akun tidak terkunci
    @Override
    public boolean isAccountNonLocked() {
        return true; // Akun tidak pernah terkunci
    }

    // Method untuk mengecek apakah kredensial (password) belum kedaluwarsa
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Kredensial tidak pernah kedaluwarsa
    }

    // Method untuk mengecek apakah akun aktif
    @Override
    public boolean isEnabled() {
        return true; // Akun selalu aktif
    }
}
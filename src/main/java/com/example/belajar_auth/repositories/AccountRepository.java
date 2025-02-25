package com.example.belajar_auth.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.belajar_auth.entities.Account;

/**
 * Repository untuk entitas Account, digunakan untuk berinteraksi dengan database.
 */
public interface AccountRepository extends JpaRepository<Account, String> { // ID menggunakan tipe String (UUID)

    /**
     * Mencari akun berdasarkan email dengan status aktif.
     *
     * @param email Email pengguna yang dicari.
     * @return Optional<Account> jika akun ditemukan.
     */
    Optional<Account> findByEmailAndActiveIsTrue(String email);

    /**
     * Memeriksa apakah akun dengan email tertentu sudah terdaftar.
     *
     * @param email Email yang ingin diperiksa.
     * @return true jika email sudah digunakan, false jika belum.
     */
    boolean existsByEmail(String email);

    /**
     * Memeriksa apakah akun dengan nomor telepon tertentu sudah terdaftar.
     *
     * @param phoneNumber Nomor telepon yang ingin diperiksa.
     * @return true jika nomor telepon sudah digunakan, false jika belum.
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Mencari akun berdasarkan email dengan status aktif menggunakan query JPQL.
     *
     * @param email Email pengguna yang dicari.
     * @return Optional<Account> jika akun ditemukan.
     */
    @Query("SELECT a FROM Account a WHERE a.email = :email AND a.active = true")
    Optional<Account> findAccountQueryByEmail(@Param("email") String email);

    /**
     * Mencari akun berdasarkan nomor telepon menggunakan query JPQL.
     *
     * @param phoneNumber Nomor telepon pengguna yang dicari.
     * @return Optional<Account> jika akun ditemukan.
     */
    @Query("SELECT a FROM Account a WHERE a.phoneNumber = :phoneNumber")
    Optional<Account> findAccountQueryByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}

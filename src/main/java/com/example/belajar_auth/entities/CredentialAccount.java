package com.example.belajar_auth.entities;

import jakarta.persistence.*;
import lombok.*;
import com.example.belajar_auth.enums.CredentialTypeEnum;

import java.util.UUID;

@Entity
@Table(name = "credential_account") // Nama tabel untuk entity ini
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialAccount {

    @Id
    @Column(name = "id")
    private String id; // Menggunakan UUID sebagai primary key

    @Column(name = "credential", nullable = false)
    private String credential; // Menyimpan informasi kredensial (misalnya, token atau password)

    @Enumerated(EnumType.STRING) // Menyimpan nilai enum sebagai string di database
    @Column(name = "credential_type", nullable = false)
    private CredentialTypeEnum type; // Tipe kredensial (misalnya, EMAIL, GOOGLE, FACEBOOK)

    @ManyToOne // Relasi many-to-one dengan entity Account
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // Referensi ke akun yang memiliki kredensial ini

    /**
     * Method yang dijalankan sebelum entitas disimpan ke database.
     * - Mengenerate UUID jika id belum ada.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString(); // Generate UUID sebagai id
        }
    }
}
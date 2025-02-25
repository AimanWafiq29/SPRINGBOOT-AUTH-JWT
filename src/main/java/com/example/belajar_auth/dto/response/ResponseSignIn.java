package com.example.belajar_auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * DTO untuk respons setelah pengguna berhasil melakukan sign-in.
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Gunakan snake_case untuk JSON
@JsonInclude(JsonInclude.Include.NON_NULL) // Field NULL tidak disertakan dalam JSON
public class ResponseSignIn {
    @NotBlank(message = "Access token must not be blank")
    private String accessToken; // Token JWT untuk autentikasi

    private String refreshToken; // Token untuk memperbarui access token (opsional)
}

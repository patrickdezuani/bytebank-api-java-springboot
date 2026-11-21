package com.bytebank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public record CriarContaRequest(
        @NotBlank(message = "O titular é obrigatório.")
        String titular,

        @DecimalMin(value = "0.00", message = "O saldo inicial não pode ser negativo.")
        BigDecimal saldoInicial
) {
}

package com.bytebank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OperacaoRequest(
        @NotNull(message = "Informe o tipo da operação.")
        String tipo,

        @NotNull(message = "Informe o valor da operação.")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero.")
        BigDecimal valor
) {
}

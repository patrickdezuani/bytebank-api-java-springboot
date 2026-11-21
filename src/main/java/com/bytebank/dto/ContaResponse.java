package com.bytebank.dto;

import com.bytebank.model.Conta;
import java.math.BigDecimal;

public record ContaResponse(Long id, String titular, BigDecimal saldo) {
    public static ContaResponse from(Conta conta) {
        return new ContaResponse(conta.getId(), conta.getTitular(), conta.getSaldo());
    }
}

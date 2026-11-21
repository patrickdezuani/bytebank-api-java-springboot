package com.bytebank.dto;

import java.math.BigDecimal;

public record OperacaoResponse(String mensagem, Long contaId, BigDecimal valor, BigDecimal saldoAtual) {
}

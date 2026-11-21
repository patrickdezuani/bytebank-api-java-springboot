package com.bytebank.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperacaoRealizadaEvent(
        Long contaId,
        String tipo,
        BigDecimal valor,
        BigDecimal saldoAtual,
        LocalDateTime dataHora
) {
}

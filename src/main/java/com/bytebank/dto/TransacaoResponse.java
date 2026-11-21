package com.bytebank.dto;

import com.bytebank.model.Transacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(Long id, String tipo, BigDecimal valor, LocalDateTime dataHora) {
    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getTipo().name(),
                transacao.getValor(),
                transacao.getDataHora()
        );
    }
}

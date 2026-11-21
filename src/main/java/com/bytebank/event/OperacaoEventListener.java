package com.bytebank.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/*
 * OBSERVER: recebe eventos publicados pela camada de serviço.
 * O Spring gerencia este componente como singleton por padrão.
 */
@Component
public class OperacaoEventListener {

    private static final Logger log = LoggerFactory.getLogger(OperacaoEventListener.class);

    @EventListener
    public void aoRealizarOperacao(OperacaoRealizadaEvent evento) {
        log.info("Operação {} na conta {}: valor={}, saldo={}, data={}",
                evento.tipo(), evento.contaId(), evento.valor(),
                evento.saldoAtual(), evento.dataHora());
    }
}

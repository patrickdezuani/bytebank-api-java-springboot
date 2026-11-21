package com.bytebank.factory;

import com.bytebank.exception.RegraNegocioException;
import com.bytebank.strategy.OperacaoStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
 * FACTORY: centraliza a escolha da estratégia.
 * O Spring injeta automaticamente os beans que implementam OperacaoStrategy.
 */
@Component
public class OperacaoFactory {

    private final Map<String, OperacaoStrategy> estrategias;

    public OperacaoFactory(Map<String, OperacaoStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public OperacaoStrategy obter(String tipo) {
        OperacaoStrategy estrategia = estrategias.get(tipo);
        if (estrategia == null) {
            throw new RegraNegocioException("Operação inválida. Use DEPOSITAR ou SACAR.");
        }
        return estrategia;
    }
}

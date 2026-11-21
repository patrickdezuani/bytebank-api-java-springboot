package com.bytebank.strategy;

import com.bytebank.model.Conta;
import java.math.BigDecimal;

public interface OperacaoStrategy {
    void executar(Conta conta, BigDecimal valor);
}

package com.bytebank.strategy;

import com.bytebank.model.Conta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("DEPOSITAR")
public class DepositoStrategy implements OperacaoStrategy {
    @Override
    public void executar(Conta conta, BigDecimal valor) {
        conta.depositar(valor);
    }
}

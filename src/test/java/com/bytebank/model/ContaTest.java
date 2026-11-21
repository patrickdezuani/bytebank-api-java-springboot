package com.bytebank.model;

import com.bytebank.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void deveDepositarValorNaConta() {
        Conta conta = new Conta("Maria", new BigDecimal("100.00"));

        conta.depositar(new BigDecimal("50.00"));

        assertEquals(0, new BigDecimal("150.00").compareTo(conta.getSaldo()));
    }

    @Test
    void deveSacarQuandoExisteSaldo() {
        Conta conta = new Conta("João", new BigDecimal("100.00"));

        conta.sacar(new BigDecimal("30.00"));

        assertEquals(0, new BigDecimal("70.00").compareTo(conta.getSaldo()));
    }

    @Test
    void naoDevePermitirSaqueMaiorQueSaldo() {
        Conta conta = new Conta("Ana", new BigDecimal("20.00"));

        assertThrows(RegraNegocioException.class,
                () -> conta.sacar(new BigDecimal("40.00")));

        assertEquals(0, new BigDecimal("20.00").compareTo(conta.getSaldo()));
    }

    @Test
    void naoDevePermitirValorZeroOuNegativo() {
        Conta conta = new Conta("Pedro", new BigDecimal("100.00"));

        assertThrows(RegraNegocioException.class,
                () -> conta.depositar(BigDecimal.ZERO));
    }
}

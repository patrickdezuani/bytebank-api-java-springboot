package com.bytebank.model;

import com.bytebank.exception.RegraNegocioException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titular;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    protected Conta() {
    }

    public Conta(String titular, BigDecimal saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial == null ? BigDecimal.ZERO : saldoInicial;
        if (this.saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("O saldo inicial não pode ser negativo.");
        }
    }

    public void depositar(BigDecimal valor) {
        validarValor(valor);
        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        validarValor(valor);
        if (valor.compareTo(saldo) > 0) {
            throw new RegraNegocioException("Saldo insuficiente.");
        }
        saldo = saldo.subtract(valor);
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("O valor da operação deve ser maior que zero.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}

package com.bytebank.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOperacao tipo;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id")
    private Conta conta;

    protected Transacao() {
    }

    public Transacao(TipoOperacao tipo, BigDecimal valor, Conta conta) {
        this.tipo = tipo;
        this.valor = valor;
        this.conta = conta;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public TipoOperacao getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getDataHora() { return dataHora; }
}

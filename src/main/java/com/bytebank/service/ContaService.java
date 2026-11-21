package com.bytebank.service;

import com.bytebank.dto.*;
import com.bytebank.event.OperacaoRealizadaEvent;
import com.bytebank.exception.RecursoNaoEncontradoException;
import com.bytebank.factory.OperacaoFactory;
import com.bytebank.model.*;
import com.bytebank.repository.ContaRepository;
import com.bytebank.repository.TransacaoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final OperacaoFactory operacaoFactory;
    private final ApplicationEventPublisher eventPublisher;

    public ContaService(
            ContaRepository contaRepository,
            TransacaoRepository transacaoRepository,
            OperacaoFactory operacaoFactory,
            ApplicationEventPublisher eventPublisher
    ) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.operacaoFactory = operacaoFactory;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public ContaResponse criar(CriarContaRequest request) {
        Conta conta = new Conta(request.titular(), request.saldoInicial());
        return ContaResponse.from(contaRepository.save(conta));
    }

    @Transactional(readOnly = true)
    public ContaResponse buscar(Long id) {
        return ContaResponse.from(buscarConta(id));
    }

    @Transactional
    public OperacaoResponse processar(Long contaId, OperacaoRequest request) {
        Conta conta = buscarConta(contaId);
        TipoOperacao tipo;

        try {
            tipo = TipoOperacao.valueOf(request.tipo().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new com.bytebank.exception.RegraNegocioException(
                    "Operação inválida. Use DEPOSITAR ou SACAR.");
        }

        // FACTORY + STRATEGY: seleciona e executa o comportamento solicitado.
        operacaoFactory.obter(tipo.name()).executar(conta, request.valor());

        contaRepository.save(conta);
        Transacao transacao = transacaoRepository.save(
                new Transacao(tipo, request.valor(), conta)
        );

        // OBSERVER: publica evento para componentes interessados.
        eventPublisher.publishEvent(new OperacaoRealizadaEvent(
                conta.getId(), tipo.name(), request.valor(),
                conta.getSaldo(), LocalDateTime.now()
        ));

        return new OperacaoResponse(
                "Operação realizada com sucesso.",
                conta.getId(),
                transacao.getValor(),
                conta.getSaldo()
        );
    }

    @Transactional(readOnly = true)
    public List<TransacaoResponse> listarTransacoes(Long contaId) {
        buscarConta(contaId);
        return transacaoRepository.findByContaIdOrderByDataHoraDesc(contaId)
                .stream()
                .map(TransacaoResponse::from)
                .toList();
    }

    private Conta buscarConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Conta não encontrada: " + id));
    }
}

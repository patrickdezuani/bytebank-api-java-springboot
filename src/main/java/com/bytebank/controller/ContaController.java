package com.bytebank.controller;

import com.bytebank.dto.*;
import com.bytebank.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaResponse criar(@Valid @RequestBody CriarContaRequest request) {
        return contaService.criar(request);
    }

    @GetMapping("/{id}")
    public ContaResponse buscar(@PathVariable Long id) {
        return contaService.buscar(id);
    }

    @PostMapping("/{id}/operacoes")
    public OperacaoResponse operar(
            @PathVariable Long id,
            @Valid @RequestBody OperacaoRequest request
    ) {
        return contaService.processar(id, request);
    }

    @GetMapping("/{id}/transacoes")
    public List<TransacaoResponse> transacoes(@PathVariable Long id) {
        return contaService.listarTransacoes(id);
    }
}

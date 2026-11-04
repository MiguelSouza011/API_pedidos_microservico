package com.miguelsouza.pedidos.controller;

import com.miguelsouza.pedidos.dto.RecebimentoCallbackPagamentoDTO;
import com.miguelsouza.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<Object> atualizarStatusPagamento(
            @RequestBody RecebimentoCallbackPagamentoDTO body,
            @RequestHeader(required = true, name = "apiKey") String apiKey) {

        service.atualizarStatusPagamento(body.codigo(), body.chavePagamento(), body.status(), body.observacoes());
        return ResponseEntity.ok().build();

    }
}

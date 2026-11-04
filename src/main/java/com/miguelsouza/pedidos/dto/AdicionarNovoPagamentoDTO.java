package com.miguelsouza.pedidos.dto;

import com.miguelsouza.pedidos.model.enums.TipoPagamento;

public record AdicionarNovoPagamentoDTO(
        Long codigoPedido,
        String dadosCartao,
        TipoPagamento tipo
) {
}

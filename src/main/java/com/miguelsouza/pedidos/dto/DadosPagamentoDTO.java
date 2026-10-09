package com.miguelsouza.pedidos.dto;

import com.miguelsouza.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipo) {
}

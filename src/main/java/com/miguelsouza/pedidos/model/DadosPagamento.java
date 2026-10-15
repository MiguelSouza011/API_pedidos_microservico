package com.miguelsouza.pedidos.model;

import com.miguelsouza.pedidos.model.enums.TipoPagamento;
import lombok.Data;

@Data
public class DadosPagamento {
    private String dados;
    private TipoPagamento tipo;
}

package com.miguelsouza.pedidos.mapper;

import com.miguelsouza.pedidos.dto.ItemPedidoDTO;
import com.miguelsouza.pedidos.model.ItemPedido;

public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}

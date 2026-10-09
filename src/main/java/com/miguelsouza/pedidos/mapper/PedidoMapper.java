package com.miguelsouza.pedidos.mapper;

import com.miguelsouza.pedidos.dto.NovoPedidoDTO;
import com.miguelsouza.pedidos.model.Pedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    Pedido map(NovoPedidoDTO dto);
}

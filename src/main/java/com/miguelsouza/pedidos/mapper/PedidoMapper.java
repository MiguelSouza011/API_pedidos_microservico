package com.miguelsouza.pedidos.mapper;

import com.miguelsouza.pedidos.dto.ItemPedidoDTO;
import com.miguelsouza.pedidos.dto.NovoPedidoDTO;
import com.miguelsouza.pedidos.model.ItemPedido;
import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.model.enums.StatusPedido;
import org.jspecify.annotations.NonNull;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(target = "dadosPagamento", source = "dadosPagamento")
    Pedido map(NovoPedidoDTO dto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream()
                .map(ITEM_PEDIDO_MAPPER::map)
                .toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido) {
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());

        var total = calcularTotal(pedido);

        pedido.setTotal(total);
    }

    private static @NonNull BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItens().stream().map(item ->
                item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))
        ).reduce(BigDecimal.ZERO, BigDecimal::add).abs();
    }
}

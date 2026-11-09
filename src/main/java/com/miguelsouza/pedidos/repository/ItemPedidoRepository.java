package com.miguelsouza.pedidos.repository;

import com.miguelsouza.pedidos.model.ItemPedido;
import com.miguelsouza.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}

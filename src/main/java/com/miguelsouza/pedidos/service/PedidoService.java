package com.miguelsouza.pedidos.service;

import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.repository.ItemPedidoRepository;
import com.miguelsouza.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;

    public Pedido save(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }

    public List<Pedido> findAll(){
        return repository.findAll();
    }
}

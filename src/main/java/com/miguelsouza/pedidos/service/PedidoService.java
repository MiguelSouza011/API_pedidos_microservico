package com.miguelsouza.pedidos.service;

import com.miguelsouza.pedidos.model.Pedido;
import com.miguelsouza.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }
}

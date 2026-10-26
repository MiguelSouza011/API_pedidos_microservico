package com.miguelsouza.pedidos.validator;

import com.miguelsouza.pedidos.client.ProdutosClient;
import com.miguelsouza.pedidos.client.representation.ProdutoRepresentation;
import com.miguelsouza.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;

    public void validar(Pedido pedido) {

    }
}

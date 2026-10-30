package com.miguelsouza.pedidos.validator;

import com.miguelsouza.pedidos.client.ClientsClient;
import com.miguelsouza.pedidos.client.ProdutosClient;
import com.miguelsouza.pedidos.client.representation.ClienteRepresentation;
import com.miguelsouza.pedidos.client.representation.ProdutoRepresentation;
import com.miguelsouza.pedidos.model.ItemPedido;
import com.miguelsouza.pedidos.model.Pedido;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientsClient clientsClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            var response = clientsClient.obterDados(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("Cliente de codigo {} encontrado: {}", cliente.codigo(), cliente.nome());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Cliente não encontrado: " + codigoCliente);
        }
    }

    private void validarItem(ItemPedido item) {
        try {
            var response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Produto de codigo {} encontrado: {}", produto.codigo(), produto.nome());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Produto não encontrado: " + item.getCodigoProduto());
        }
    }
}

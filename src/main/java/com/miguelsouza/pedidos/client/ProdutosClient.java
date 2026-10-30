package com.miguelsouza.pedidos.client;

import com.miguelsouza.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtos", url = "${miguelsouza.pedidos.clients.produtos.url}")
public interface ProdutosClient {

    @GetMapping("{codigo}")
    ResponseEntity<ProdutoRepresentation> obterDados(@PathVariable Long codigo);
}

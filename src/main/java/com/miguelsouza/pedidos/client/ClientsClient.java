package com.miguelsouza.pedidos.client;

import com.miguelsouza.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes", url = "${miguelsouza.pedidos.clients.clientes.url}")
public interface ClientsClient {

    @GetMapping("{codigo}")
    ResponseEntity<ClienteRepresentation> obterDados(@PathVariable Long codigo);
}

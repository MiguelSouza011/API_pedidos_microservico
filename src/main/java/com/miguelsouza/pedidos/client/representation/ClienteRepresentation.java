package com.miguelsouza.pedidos.client.representation;

public record ClienteRepresentation(
        Long codigo,
        String nome,
        String cpf,
        String numero,
        String logradouro,
        String bairro,
        String email,
        String telefone
) {
}

package com.miguelsouza.pedidos.exception;

public class ItemNaoEncontradoException extends RuntimeException {

    public ItemNaoEncontradoException(String message) {
        super(message);
    }
}

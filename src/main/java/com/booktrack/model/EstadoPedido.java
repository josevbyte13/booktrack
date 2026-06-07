package com.booktrack.model;

public enum EstadoPedido {
    PREPARANDO("Preparando"),
    EN_RUTA("En Ruta"),
    POR_LLEGAR("Por Llegar"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
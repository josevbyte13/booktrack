package com.booktrack.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private String idSeguimiento;
    private String nombreCliente;
    private String telefonoCliente;
    private String direccionEntrega;
    private String librosTitulos;
    private double montoTotal;
    private EstadoPedido estado;
    private Repartidor repartidor;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String mensajeIncidencia;

    public Pedido(String idSeguimiento, String nombreCliente, String telefonoCliente,
                  String direccionEntrega, String librosTitulos, double montoTotal) {
        this.idSeguimiento = idSeguimiento;
        this.nombreCliente = nombreCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionEntrega = direccionEntrega;
        this.librosTitulos = librosTitulos;
        this.montoTotal = montoTotal;
        this.estado = EstadoPedido.PREPARANDO;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.mensajeIncidencia = "";
    }

    public void actualizarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        this.fechaActualizacion = LocalDateTime.now();
        this.mensajeIncidencia = "";
    }

    public void reportarIncidencia(String descripcion) {
        this.mensajeIncidencia = descripcion;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getFechaCreacionFormateada() {
        return fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getFechaActualizacionFormateada() {
        return fechaActualizacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getIdSeguimiento() { return idSeguimiento; }
    public String getNombreCliente() { return nombreCliente; }
    public String getTelefonoCliente() { return telefonoCliente; }
    public String getDireccionEntrega() { return direccionEntrega; }
    public String getLibrosTitulos() { return librosTitulos; }
    public double getMontoTotal() { return montoTotal; }
    public EstadoPedido getEstado() { return estado; }
    public Repartidor getRepartidor() { return repartidor; }
    public void setRepartidor(Repartidor repartidor) { this.repartidor = repartidor; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public String getMensajeIncidencia() { return mensajeIncidencia; }
    public boolean tieneIncidencia() { return mensajeIncidencia != null && !mensajeIncidencia.isEmpty(); }
}
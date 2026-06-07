package com.booktrack.service;

import com.booktrack.model.EstadoPedido;
import com.booktrack.model.Pedido;
import com.booktrack.model.Repartidor;
import com.booktrack.repository.Repositorio;
import com.booktrack.util.GeneradorID;

import java.util.List;

public class PedidoService {

    private final Repositorio repositorio = Repositorio.getInstance();

    public String crearPedido(String nombreCliente, String telefonoCliente,
                              String direccion, String libros, double monto) {
        String id = GeneradorID.generarIdSeguimiento();
        Pedido pedido = new Pedido(id, nombreCliente, telefonoCliente, direccion, libros, monto);
        repositorio.guardarPedido(pedido);
        return id;
    }

    public boolean asignarRepartidor(String idPedido, String idRepartidor) {
        Pedido pedido = repositorio.buscarPedido(idPedido);
        Repartidor repartidor = repositorio.buscarRepartidor(idRepartidor);
        if (pedido == null || repartidor == null) return false;
        if (!repartidor.isDisponible()) return false;
        pedido.setRepartidor(repartidor);
        repartidor.setDisponible(false);
        return true;
    }

    public boolean avanzarEstado(String idPedido) {
        Pedido pedido = repositorio.buscarPedido(idPedido);
        if (pedido == null) return false;
        switch (pedido.getEstado()) {
            case PREPARANDO:  pedido.actualizarEstado(EstadoPedido.EN_RUTA); break;
            case EN_RUTA:     pedido.actualizarEstado(EstadoPedido.POR_LLEGAR); break;
            case POR_LLEGAR:
                pedido.actualizarEstado(EstadoPedido.ENTREGADO);
                if (pedido.getRepartidor() != null) pedido.getRepartidor().setDisponible(true);
                break;
            case ENTREGADO: return false;
        }
        return true;
    }

    public boolean reportarIncidencia(String idPedido, String descripcion) {
        Pedido pedido = repositorio.buscarPedido(idPedido);
        if (pedido == null) return false;
        pedido.reportarIncidencia(descripcion);
        return true;
    }

    public Pedido consultarPedido(String idSeguimiento) {
        return repositorio.buscarPedido(idSeguimiento);
    }

    public List<Pedido> obtenerTodosPedidos() { return repositorio.obtenerTodosPedidos(); }
    public List<Pedido> obtenerPedidosActivos() { return repositorio.obtenerPedidosActivos(); }
    public List<Repartidor> obtenerRepartidoresDisponibles() { return repositorio.obtenerRepartidoresDisponibles(); }
    public List<Repartidor> obtenerTodosRepartidores() { return repositorio.obtenerTodosRepartidores(); }
}
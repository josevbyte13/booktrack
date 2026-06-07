package com.booktrack.repository;

import com.booktrack.model.Pedido;
import com.booktrack.model.Repartidor;
import com.booktrack.util.GeneradorID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repositorio {

    private static Repositorio instancia;
    private final Map<String, Pedido> pedidos = new HashMap<>();
    private final Map<String, Repartidor> repartidores = new HashMap<>();

    private Repositorio() {
        cargarRepartidoresEjemplo();
    }

    public static Repositorio getInstance() {
        if (instancia == null) {
            instancia = new Repositorio();
        }
        return instancia;
    }

    public void guardarPedido(Pedido pedido) {
        pedidos.put(pedido.getIdSeguimiento(), pedido);
    }

    public Pedido buscarPedido(String id) {
        return pedidos.get(id.toUpperCase());
    }

    public List<Pedido> obtenerTodosPedidos() {
        return new ArrayList<>(pedidos.values());
    }

    public List<Pedido> obtenerPedidosActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos.values()) {
            if (p.getEstado() != com.booktrack.model.EstadoPedido.ENTREGADO) {
                activos.add(p);
            }
        }
        return activos;
    }

    public void guardarRepartidor(Repartidor r) {
        repartidores.put(r.getId(), r);
    }

    public Repartidor buscarRepartidor(String id) {
        return repartidores.get(id);
    }

    public List<Repartidor> obtenerRepartidoresDisponibles() {
        List<Repartidor> disponibles = new ArrayList<>();
        for (Repartidor r : repartidores.values()) {
            if (r.isDisponible()) disponibles.add(r);
        }
        return disponibles;
    }

    public List<Repartidor> obtenerTodosRepartidores() {
        return new ArrayList<>(repartidores.values());
    }

    private void cargarRepartidoresEjemplo() {
        Repartidor r1 = new Repartidor(GeneradorID.generarIdRepartidor(),
                "Carlos Mendoza", "0412-5551234", "Moto Honda CB", "AB123CD");
        Repartidor r2 = new Repartidor(GeneradorID.generarIdRepartidor(),
                "Luis García", "0424-5559876", "Moto Yamaha", "XY456ZW");
        Repartidor r3 = new Repartidor(GeneradorID.generarIdRepartidor(),
                "Pedro Rojas", "0416-5554567", "Carro Corolla", "MN789QR");
        guardarRepartidor(r1);
        guardarRepartidor(r2);
        guardarRepartidor(r3);
    }
}
package com.booktrack.model;

public class Repartidor {
    private String id;
    private String nombre;
    private String telefono;
    private String vehiculo;
    private String placaVehiculo;
    private boolean disponible;

    public Repartidor(String id, String nombre, String telefono, String vehiculo, String placaVehiculo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.vehiculo = vehiculo;
        this.placaVehiculo = placaVehiculo;
        this.disponible = true;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getVehiculo() { return vehiculo; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return nombre + " | " + vehiculo + " (" + placaVehiculo + ")";
    }
}
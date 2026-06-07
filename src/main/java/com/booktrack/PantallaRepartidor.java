package com.booktrack;

import com.booktrack.model.EstadoPedido;
import com.booktrack.model.Pedido;
import com.booktrack.service.PedidoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PantallaRepartidor {

    private final PedidoService servicio = new PedidoService();

    public void mostrar(Stage stage) {
        Text titulo = new Text("PANEL REPARTIDOR");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #3b82f6;");

        TextField txtId = new TextField();
        txtId.setPromptText("ID del pedido (ej: BT-XXXXXXXX)");
        txtId.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: #f0ece4; -fx-border-color: #2a2a2a; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        Label lblEstado = new Label("");
        lblEstado.setStyle("-fx-text-fill: #f0ece4; -fx-font-size: 13px;");
        lblEstado.setWrapText(true);

        Button btnAvanzar = boton("✔ Avanzar Estado", "#3b82f6");
        btnAvanzar.setOnAction(e -> {
            String id = txtId.getText().trim().toUpperCase();
            Pedido pedido = servicio.consultarPedido(id);
            if (pedido == null) {
                lblEstado.setText("⚠ Pedido no encontrado.");
                lblEstado.setStyle("-fx-text-fill: #ef4444;");
                return;
            }
            EstadoPedido antes = pedido.getEstado();
            if (servicio.avanzarEstado(id)) {
                lblEstado.setText("✔ " + antes.getDescripcion() + " → " + pedido.getEstado().getDescripcion());
                lblEstado.setStyle("-fx-text-fill: #22c55e; -fx-font-size: 13px;");
            } else {
                lblEstado.setText("⚠ El pedido ya fue entregado.");
                lblEstado.setStyle("-fx-text-fill: #ef4444;");
            }
        });

        Button btnTrafico = botonIncidencia("🚗 Tráfico en autopista");
        Button btnLluvia  = botonIncidencia("🌧 Lluvia fuerte");
        Button btnVehiculo = botonIncidencia("🔧 Problema con vehículo");

        btnTrafico.setOnAction(e -> reportar(txtId.getText(), "⚠ Retraso por tráfico en autopista.", lblEstado));
        btnLluvia.setOnAction(e -> reportar(txtId.getText(), "⚠ Retraso por lluvia fuerte.", lblEstado));
        btnVehiculo.setOnAction(e -> reportar(txtId.getText(), "⚠ Inconveniente con el vehículo.", lblEstado));

        Button btnVolver = boton("← Volver", "#1f1f1f");
        btnVolver.setOnAction(e -> new MainApp().start(stage));

        Label lblIncidencias = new Label("REPORTAR INCIDENCIA");
        lblIncidencias.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 11px; -fx-font-weight: bold;");

        VBox root = new VBox(14, titulo,
            etiqueta("ID DEL PEDIDO"), txtId,
            btnAvanzar, lblEstado,
            new Separator(), lblIncidencias,
            btnTrafico, btnLluvia, btnVehiculo,
            new Separator(), btnVolver);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #0d0d0d;");

        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("BookTrack — Repartidor");
    }

    private void reportar(String id, String msg, Label lbl) {
        if (servicio.reportarIncidencia(id.trim().toUpperCase(), msg)) {
            lbl.setText("✔ Incidencia reportada.");
            lbl.setStyle("-fx-text-fill: #f5a623;");
        } else {
            lbl.setText("⚠ Pedido no encontrado.");
            lbl.setStyle("-fx-text-fill: #ef4444;");
        }
    }

    private Label etiqueta(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: #7a7570; -fx-font-size: 10px; -fx-font-weight: bold;");
        return l;
    }

    private Button boton(String texto, String bg) {
        Button b = new Button(texto);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #f0ece4; -fx-font-weight: bold; -fx-border-color: #3b82f6; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        return b;
    }

    private Button botonIncidencia(String texto) {
        Button b = new Button(texto);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: #2d1010; -fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-border-color: #ef4444; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        return b;
    }
}
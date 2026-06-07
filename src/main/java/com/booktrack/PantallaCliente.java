package com.booktrack;

import com.booktrack.model.EstadoPedido;
import com.booktrack.model.Pedido;
import com.booktrack.model.Repartidor;
import com.booktrack.service.PedidoService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PantallaCliente {

    private final PedidoService servicio = new PedidoService();

    public void mostrar(Stage stage) {
        Text titulo = new Text("CONSULTAR PEDIDO");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #22c55e;");

        TextField txtId = new TextField();
        txtId.setPromptText("Ingresa tu ID de seguimiento");
        txtId.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: #f0ece4; -fx-border-color: #2a2a2a; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        VBox resultado = new VBox(10);
        resultado.setStyle("-fx-background-color: #161616; -fx-border-color: #2a2a2a; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 16;");
        resultado.setVisible(false);

        Button btnConsultar = new Button("🔍 Consultar");
        btnConsultar.setMaxWidth(Double.MAX_VALUE);
        btnConsultar.setStyle("-fx-background-color: #22c55e; -fx-text-fill: #000; -fx-font-weight: bold; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");

        Label lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #ef4444;");

        btnConsultar.setOnAction(e -> {
            String id = txtId.getText().trim().toUpperCase();
            Pedido pedido = servicio.consultarPedido(id);
            if (pedido == null) {
                lblError.setText("⚠ ID no encontrado. Verifica con la librería.");
                resultado.setVisible(false);
                return;
            }
            lblError.setText("");
            resultado.getChildren().clear();
            resultado.getChildren().addAll(
                fila("Cliente",   pedido.getNombreCliente()),
                fila("Libro(s)", pedido.getLibrosTitulos()),
                fila("Monto",    "$" + String.format("%.2f", pedido.getMontoTotal())),
                fila("Dirección",pedido.getDireccionEntrega()),
                new Separator(),
                progreso(pedido),
                new Separator()
            );
            if (pedido.getRepartidor() != null) {
                Repartidor r = pedido.getRepartidor();
                resultado.getChildren().addAll(
                    fila("Repartidor", r.getNombre()),
                    fila("Vehículo",   r.getVehiculo()),
                    fila("Placa",      r.getPlacaVehiculo()),
                    fila("Tel. Rep.",  r.getTelefono())
                );
            } else {
                resultado.getChildren().add(fila("Repartidor", "Pendiente de asignación"));
            }
            if (pedido.tieneIncidencia()) {
                Label inc = new Label("⚠ " + pedido.getMensajeIncidencia());
                inc.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                inc.setWrapText(true);
                resultado.getChildren().add(inc);
            }
            resultado.setVisible(true);
        });

        Button btnVolver = new Button("← Volver");
        btnVolver.setMaxWidth(Double.MAX_VALUE);
        btnVolver.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: #f0ece4; -fx-border-color: #22c55e; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        btnVolver.setOnAction(e -> new MainApp().start(stage));

        VBox root = new VBox(14, titulo,
            etiqueta("ID DE SEGUIMIENTO"), txtId,
            btnConsultar, lblError, resultado, btnVolver);
        root.setAlignment(Pos.CENTER_LEFT);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #0d0d0d;");

        ScrollPane scroll = new ScrollPane(root);
        scroll.setStyle("-fx-background: #0d0d0d; -fx-background-color: #0d0d0d;");
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 500, 650);
        stage.setScene(scene);
        stage.setTitle("BookTrack — Cliente");
    }

    private HBox fila(String clave, String valor) {
        Label k = new Label(clave + ":");
        k.setStyle("-fx-text-fill: #7a7570; -fx-font-size: 11px; -fx-min-width: 90;");
        Label v = new Label(valor);
        v.setStyle("-fx-text-fill: #f0ece4; -fx-font-size: 13px;");
        v.setWrapText(true);
        HBox h = new HBox(10, k, v);
        h.setAlignment(Pos.CENTER_LEFT);
        return h;
    }

    private HBox progreso(Pedido pedido) {
        String[] nombres = {"Preparando", "En Ruta", "Por Llegar", "Entregado"};
        EstadoPedido[] estados = EstadoPedido.values();
        HBox hbox = new HBox(6);
        hbox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < nombres.length; i++) {
            boolean activo = pedido.getEstado() == estados[i];
            boolean pasado = pedido.getEstado().ordinal() > i;
            Label l = new Label((pasado ? "✔ " : activo ? "▶ " : "○ ") + nombres[i]);
            l.setStyle("-fx-text-fill: " + (activo ? "#f5a623" : pasado ? "#22c55e" : "#7a7570") + "; -fx-font-size: 11px; -fx-font-weight: " + (activo ? "bold" : "normal") + ";");
            hbox.getChildren().add(l);
            if (i < nombres.length - 1) {
                Label sep = new Label("→");
                sep.setStyle("-fx-text-fill: #2a2a2a;");
                hbox.getChildren().add(sep);
            }
        }
        return hbox;
    }

    private Label etiqueta(String texto) {
        Label l = new Label(texto);
        l.setStyle("-fx-text-fill: #7a7570; -fx-font-size: 10px; -fx-font-weight: bold;");
        return l;
    }
}
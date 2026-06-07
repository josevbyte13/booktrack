package com.booktrack;

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

public class PantallaEmpleado {

    private final PedidoService servicio = new PedidoService();

    public void mostrar(Stage stage) {
        Text titulo = new Text("PANEL EMPLEADO");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #f5a623;");

        TextField txtNombre    = campo("Nombre del cliente");
        TextField txtTelefono  = campo("Teléfono");
        TextField txtDireccion = campo("Dirección de entrega");
        TextField txtLibros    = campo("Libro(s)");
        TextField txtMonto     = campo("Monto ($)");

        Label lblId = new Label("");
        lblId.setStyle("-fx-text-fill: #22c55e; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button btnCrear = boton("Crear Pedido", "#f5a623");
        btnCrear.setOnAction(e -> {
            try {
                double monto = Double.parseDouble(txtMonto.getText());
                String id = servicio.crearPedido(
                    txtNombre.getText(), txtTelefono.getText(),
                    txtDireccion.getText(), txtLibros.getText(), monto);
                lblId.setText("✔ ID de Seguimiento: " + id);
                txtNombre.clear(); txtTelefono.clear();
                txtDireccion.clear(); txtLibros.clear(); txtMonto.clear();
            } catch (Exception ex) {
                lblId.setText("⚠ Verifica los datos ingresados.");
                lblId.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px;");
            }
        });

        Button btnVolver = boton("← Volver", "#1f1f1f");
        btnVolver.setOnAction(e -> new MainApp().start(stage));

        VBox form = new VBox(12, titulo,
            etiqueta("Nombre"), txtNombre,
            etiqueta("Teléfono"), txtTelefono,
            etiqueta("Dirección"), txtDireccion,
            etiqueta("Libros"), txtLibros,
            etiqueta("Monto"), txtMonto,
            btnCrear, lblId, btnVolver);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(40));
        form.setStyle("-fx-background-color: #0d0d0d;");
        form.setMaxWidth(420);

        ScrollPane scroll = new ScrollPane(form);
        scroll.setStyle("-fx-background: #0d0d0d; -fx-background-color: #0d0d0d;");
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 500, 600);
        stage.setScene(scene);
        stage.setTitle("BookTrack — Empleado");
    }

    private TextField campo(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-background-color: #1f1f1f; -fx-text-fill: #f0ece4; -fx-border-color: #2a2a2a; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");
        return tf;
    }

    private Label etiqueta(String texto) {
        Label l = new Label(texto.toUpperCase());
        l.setStyle("-fx-text-fill: #7a7570; -fx-font-size: 10px; -fx-font-weight: bold;");
        return l;
    }

    private Button boton(String texto, String bg) {
        Button b = new Button(texto);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #f0ece4; -fx-font-weight: bold; -fx-border-color: #f5a623; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-cursor: hand;");
        return b;
    }
}
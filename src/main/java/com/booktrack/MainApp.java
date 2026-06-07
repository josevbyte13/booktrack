package com.booktrack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Text titulo = new Text("BOOKTRACK");
        titulo.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-fill: #f5a623;");

        Text subtitulo = new Text("Sistema de Gestion de Entregas - El Mundo del Libro");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-fill: #7a7570;");

        Button btnEmpleado   = crearBoton("Soy Empleado",     "#1f1f1f", "#f5a623");
        Button btnRepartidor = crearBoton("Soy Repartidor",   "#1f1f1f", "#3b82f6");
        Button btnCliente    = crearBoton("Consultar Pedido", "#1f1f1f", "#22c55e");

        btnEmpleado.setOnAction(e -> new PantallaEmpleado().mostrar(stage));
        btnRepartidor.setOnAction(e -> new PantallaRepartidor().mostrar(stage));
        btnCliente.setOnAction(e -> new PantallaCliente().mostrar(stage));

        VBox root = new VBox(20, titulo, subtitulo, btnEmpleado, btnRepartidor, btnCliente);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60));
        root.setStyle("-fx-background-color: #0d0d0d;");

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("BookTrack");
        stage.setScene(scene);
        stage.show();
    }

    private Button crearBoton(String texto, String bg, String color) {
        Button btn = new Button(texto);
        btn.setMaxWidth(300);
        btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: " + color + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-border-color: " + color + "; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 12 24; -fx-cursor: hand;");
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class LoginController implements Initializable {
    @FXML
    private Button buttonInicioSesion;

    @FXML
    private TextField tfUsuario;

    @FXML
    private TextField tfContrasena;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonInicioSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Clinica.IniciarSesion(event,tfUsuario.getText(),tfContrasena.getText());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

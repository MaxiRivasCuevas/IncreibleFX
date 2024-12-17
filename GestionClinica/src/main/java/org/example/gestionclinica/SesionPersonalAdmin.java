package org.example.gestionclinica;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SesionPersonalAdmin implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableEmpleados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPA(event, "Login.fxml", "Inicio de Sesion!", null,null,null,9);
            }
        });
    }

    public void setInfo(String usuario, String Empleados) {
        lableUsuario.setText(usuario);
        lableEmpleados.setText(Empleados);
    }
}

package org.example.gestionclinica;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SesionPersonalAdmin implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonBuscar;

    @FXML
    private Button buttonAgregarEmp;

    @FXML
    private TextField tfNomEmp;

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

        buttonBuscar.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.detalleEmpleado(event,lableUsuario.getText(),tfNomEmp.getText());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setInfo(String usuario, String Empleados) {
        lableUsuario.setText(usuario);
        lableEmpleados.setText(Empleados);
    }
}

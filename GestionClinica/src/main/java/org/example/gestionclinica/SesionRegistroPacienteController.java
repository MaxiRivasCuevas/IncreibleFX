package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class SesionRegistroPacienteController implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonCambioContrasena;

    @FXML
    private Button buttonAgregarPaciente;

    @FXML
    private Button buttonAgregarCita;

    @FXML
    private Button buttonAgregarPacienteYCita;

    @FXML
    private Button buttonCancelarCita;

    @FXML
    private Label lableUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPA(event, "Login.fxml", "Inicio de Sesion!", null, 9);
            }
        });

        buttonCambioContrasena.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.cambiarContrasenaEmpleado(event, lableUsuario.getText());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonAgregarPaciente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.agregarPaciente(event);
            }
        });

        buttonAgregarCita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.agregarCita(event);
            }
        });
    }

    public void setInfo(String usuario) {
        lableUsuario.setText(usuario);
    }
}
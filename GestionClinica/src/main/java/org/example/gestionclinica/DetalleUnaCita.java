package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DetalleUnaCita implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonAgregaEnfCron;

    @FXML
    private Button buttonAgregaHist;

    @FXML
    private TextField tfEnfCronica;

    @FXML
    private TextArea tfHistorial;

    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableCita;

    @FXML
    private Label lablePaciente;

    @FXML
    private Label lableRol;

    @FXML
    private Label lableEspecialidad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPA(event, "Login.fxml", "Inicio de Sesion!", null,null,null,9);
            }
        });
    }
    public void setInfo(String usuario, String cita, String paciente, String rol, String especialidad) {
        lableUsuario.setText(usuario);
        lableCita.setText(cita);
        lablePaciente.setText(paciente);
        lableRol.setText(rol);
        lableEspecialidad.setText(especialidad);
    }
}
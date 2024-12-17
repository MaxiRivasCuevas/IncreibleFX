package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class SesionPaciente implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableRut;

    @FXML
    private Label labelEdad;

    @FXML
    private Label lableCitas;

    @FXML
    private Label lableEnfermedadesCronicas;

    @FXML
    private Label lableMedicoTrantante;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPaciente(event, "Login.fxml", "Inicio de Sesion!", null,null,null);
            }
        });
    }

    public void setInfo(String usuario, String Rut, int edad, String citas, String enfermedadesCronicas, String medicoTrantante) {
        lableUsuario.setText(usuario);
        lableRut.setText(Rut);
        labelEdad.setText(String.valueOf(edad));
        lableCitas.setText(citas);
        lableEnfermedadesCronicas.setText(enfermedadesCronicas);
        lableMedicoTrantante.setText(medicoTrantante);
    }
}

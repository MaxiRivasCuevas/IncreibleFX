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

public class SesionPaciente implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonCambioContrasena;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPaciente(event, "Login.fxml", "Inicio de Sesion!", null,null,null);
            }
        });

        buttonCambioContrasena.setOnAction(new EventHandler<javafx.event.ActionEvent>(){
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.cambiarContrasenaPaciente(event,lableUsuario.getText());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setInfo(String usuario, String Rut, int edad, String citas, String enfermedadesCronicas) {
        lableUsuario.setText(usuario);
        lableRut.setText(Rut);
        labelEdad.setText(String.valueOf(edad));
        lableCitas.setText(citas);
        lableEnfermedadesCronicas.setText(enfermedadesCronicas);
    }
}

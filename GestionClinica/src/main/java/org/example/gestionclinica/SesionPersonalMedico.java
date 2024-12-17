package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class SesionPersonalMedico implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonBusqueda;

    @FXML
    private TextField tfNumCita;

    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableCitas;

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
        buttonBusqueda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.verDetalleCita(event,lableUsuario.getText(),Integer.parseInt(tfNumCita.getText()));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setInfo(String usuario, String citas, String rol, String especialidad) {
        lableUsuario.setText(usuario);
        lableCitas.setText(citas);
        lableRol.setText(rol);
        lableEspecialidad.setText(especialidad);
    }
}

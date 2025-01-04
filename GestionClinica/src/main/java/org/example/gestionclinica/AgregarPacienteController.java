package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestionclinica.clientes.Paciente;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class AgregarPacienteController implements Initializable {

    @FXML
    private Button buttonAgregar;

    @FXML
    private TextField tfIdPaciente;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfEdad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAgregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tfIdPaciente.getText().isEmpty() && !tfNombre.getText().isEmpty() && !tfContrasena.getText().isEmpty() && !tfEdad.getText().isEmpty()) {
                    if (esInt(tfEdad.getText())) {
                        if (Integer.parseInt(tfEdad.getText()) >= 0 && Integer.parseInt(tfEdad.getText()) <= 125) {
                            try {
                                Paciente.registarPaciente(tfIdPaciente.getText(), tfNombre.getText(), tfContrasena.getText(), Integer.parseInt(tfEdad.getText()));
                                Stage stage = (Stage) buttonAgregar.getScene().getWindow();
                                stage.close();
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Edad no valida!");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Edad no valida!");
                            alert.show();
                        }
                    } else {
                        System.out.println("La edad solo puede ser numeros enteros!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("La edad solo puede ser numeros enteros!");
                        alert.show();
                    }
                } else {
                    System.out.println("Faltan datos!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Faltan datos!");
                    alert.show();
                }
            }
        });
    }

    private boolean esInt(String n) {
        int i = 0;
        try {
            i = Integer.parseInt(n);
            return true;
        } catch (Exception InputMismatchException) {
            System.out.println("Ingrese solo un numero!!!");
        }
        return false;
    }
}
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
    private Button buttonAgregaEnfCron;

    @FXML
    private Button buttonAgregaHist;

    @FXML
    private TextField tfEnfCronica;

    @FXML
    private TextArea tfHistorial;

    @FXML
    private Label lableCita;

    @FXML
    private Label lablePaciente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setInfo(String cita, String paciente) {
        lableCita.setText(cita);
        lablePaciente.setText(paciente);
    }
}
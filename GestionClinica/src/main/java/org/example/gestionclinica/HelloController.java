package org.example.gestionclinica;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.concurrent.ExecutionException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws ExecutionException, InterruptedException {
        Clinica clinicaIncreible = new Clinica();
        welcomeText.setText(clinicaIncreible.infoRAW("Maximiliano Rivas"));
    }
}
package org.example.gestionclinica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DetalleUnEmpleado implements Initializable {
    @FXML
    private Button buttonTomarVaca;

    @FXML
    private Button buttonFiniquito;

    @FXML
    private Button buttonAmonestar;

    @FXML
    private Button buttonFelicitar;

    @FXML
    private Button buttonCambioSueldo;

    @FXML
    private Button buttonCambioRol;

    @FXML
    private Button buttonDespedir;

    @FXML
    private Label lableEmpleado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setInfo(String info) {
        System.out.println(info);
        lableEmpleado.setText(info);
    }
}

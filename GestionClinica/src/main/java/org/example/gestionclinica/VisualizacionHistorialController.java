package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizacionHistorialController implements Initializable {
    @FXML
    private Label lableHistorial;

    @FXML
    private Button buttonCerrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) buttonCerrar.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setInfo(String historial) {
        lableHistorial.setText(historial);
    }
}
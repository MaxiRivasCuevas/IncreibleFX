package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.gestionclinica.clientes.Paciente;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DetalleUnaCita implements Initializable {
    @FXML
    private Button buttonAgregaEnfCron;

    @FXML
    private Button buttonAgregaHist;

    @FXML
    private Button buttonDetallePaciente;

    @FXML
    private TextField tfEnfCronica;

    @FXML
    private TextArea tfHistorial;

    @FXML
    private Label lableCita;

    @FXML
    private Label lablePaciente;

    private Paciente paciente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonAgregaHist.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!tfHistorial.getText().isEmpty()){
                    String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    paciente.agregarInfo("Dia "+ fechaActual + ": " + tfHistorial.getText());
                } else {
                    System.out.println("Ingrese algun entrada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese algun entrada!");
                    alert.show();
                }
            }
        });

        buttonAgregaEnfCron.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!tfEnfCronica.getText().isEmpty()){
                    String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    paciente.agregarInfo("Dia "+ fechaActual + " se diagnostica con: " + tfEnfCronica.getText() + "\n");
                    paciente.agregarEnfermedadCronica(tfEnfCronica.getText() + "\n");
                } else {
                    System.out.println("Ingrese algun entrada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese algun entrada!");
                    alert.show();
                }
            }
        });

        buttonDetallePaciente.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Historial del Empleado");
                alert.setHeaderText("Historial: ");
                alert.setContentText(paciente.getHistorial());
                alert.setWidth(700);
                alert.setHeight(700);
                alert.show();
                System.out.println("Mostrando Historial del Empleado!");
            }
        });
    }
    public void setInfo(String cita, Paciente paciente) {
        lableCita.setText(cita);
        lablePaciente.setText(paciente.getNombre());
        this.paciente = paciente;
    }
}
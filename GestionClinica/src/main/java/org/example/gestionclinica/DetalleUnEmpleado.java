package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.RRHH.PersonalAdmin;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.RRHH.PersonalNoMedicoInterno;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class DetalleUnEmpleado implements Initializable {
    @FXML
    private TextField tfCambio;

    @FXML
    private TextField tfDiasVacaciones;

    @FXML
    private TextArea tfAmonestacionOFelicitacion;

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

    private Funcionario funcionario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonDespedir.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Clinica.eliminarEmpleado(funcionario.getIDFuncionario());

                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonTomarVaca.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (esInt(tfDiasVacaciones.getText())) {
                    boolean operacionExitosa = false;
                    if (Integer.parseInt(tfDiasVacaciones.getText()) >= 1) {
                        if (funcionario.getClass() == PersonalMedico.class) {
                            if (Integer.parseInt(tfDiasVacaciones.getText()) <= ((PersonalMedico) funcionario).getVacaciones()) {
                                operacionExitosa = ((PersonalMedico) funcionario).tomarVacaciones(Integer.parseInt(tfDiasVacaciones.getText()));
                            }
                        } else if (funcionario.getClass() == PersonalAdmin.class) {
                            if (Integer.parseInt(tfDiasVacaciones.getText()) <= ((PersonalAdmin) funcionario).getVacaciones()) {
                                operacionExitosa = ((PersonalAdmin) funcionario).tomarVacaciones(Integer.parseInt(tfDiasVacaciones.getText()));
                            }
                        } else if (funcionario.getClass() == PersonalNoMedicoInterno.class) {
                            if (Integer.parseInt(tfDiasVacaciones.getText()) <= ((PersonalNoMedicoInterno) funcionario).getVacaciones()) {
                                operacionExitosa = ((PersonalNoMedicoInterno) funcionario).tomarVacaciones(Integer.parseInt(tfDiasVacaciones.getText()));
                            }
                        }
                    } else {
                        System.out.println("Numero de vacaciones debe ser mayor que 0!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Numero de vacaciones debe ser mayor que 0!");
                        alert.show();
                    }
                    if (!operacionExitosa && Integer.parseInt(tfDiasVacaciones.getText()) > 0) {
                        System.out.println("No puede tomar vacaciones por mas dias de los disponibles!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("No puede tomar vacaciones por mas dias de los disponibles!");
                        alert.show();
                    }
                }
            }
        });

        buttonCambioSueldo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (esInt(tfCambio.getText())) {
                    try {
                        funcionario.actualizarSueldoBruto(Integer.parseInt(tfCambio.getText()));
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("El sueldo solo puede ser un numero entero!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El sueldo solo puede ser un numero entero!");
                    alert.show();
                }
            }
        });

        buttonCambioRol.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!tfCambio.getText().isEmpty()) {
                    try {
                        funcionario.actualizarRol(tfCambio.getText());
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Ingrese algun rol!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese algun rol!");
                    alert.show();
                }
            }
        });

        buttonAmonestar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!tfAmonestacionOFelicitacion.getText().isEmpty()) {
                    funcionario.amonestar("Amonestacion el " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " por: " + tfAmonestacionOFelicitacion.getText() + "\n");
                    System.out.println("Amonestacion Realizada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Amonestacion Realizada!");
                    alert.show();
                } else {
                    System.out.println("Ingrese algun entrada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese algun entrada!");
                    alert.show();
                }
            }
        });

        buttonFelicitar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!tfAmonestacionOFelicitacion.getText().isEmpty()) {
                    funcionario.felicitar("Felicitacion el " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " por: " + tfAmonestacionOFelicitacion.getText() + "\n");
                    System.out.println("Felicitacion Realizada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Felicitacion Realizada!");
                    alert.show();
                } else {
                    System.out.println("Ingrese algun entrada!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese algun entrada!");
                    alert.show();
                }
            }
        });
    }
    public void setInfo(String info, Funcionario funcionario) {
        System.out.println(info);
        lableEmpleado.setText(info);
        this.funcionario = funcionario;
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

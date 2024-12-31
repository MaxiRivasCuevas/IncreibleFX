package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.gestionclinica.RRHH.Funcionario;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class DetalleUnEmpleado implements Initializable {
    @FXML
    private TextField tfCambio;

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

        buttonCambioSueldo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    funcionario.actualizarSueldoBruto(Integer.parseInt(tfCambio.getText()));
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonCambioRol.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    funcionario.actualizarRol(tfCambio.getText());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setInfo(String info, Funcionario funcionario) {
        System.out.println(info);
        lableEmpleado.setText(info);
        this.funcionario = funcionario;
    }
}

package org.example.gestionclinica;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.clientes.Paciente;

public class SesionPersonalAdmin implements Initializable {
    @FXML
    private Button buttonCerrarSesion;

    @FXML
    private Button buttonBuscar;

    @FXML
    private Button buttonAgregarEmp;

    @FXML
    private TextField tfNomEmp;

    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableEmpleados;

    private java.util.ArrayList<Funcionario> funcionarios;
    private ArrayList<Paciente> pacientes;
    int nivelAcceso;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPA(event, "Login.fxml", "Inicio de Sesion!", null,null,null,9);
            }
        });

        buttonBuscar.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.detalleEmpleado(event,lableUsuario.getText(),tfNomEmp.getText());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonAgregarEmp.setOnAction(new EventHandler<javafx.event.ActionEvent>(){
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.agregarEmpleado(event,lableUsuario.getText(),funcionarios,pacientes,nivelAcceso);
            }
        });
    }

    public void setInfo(String usuario, String Empleados, java.util.ArrayList<Funcionario> funcionarios,ArrayList<Paciente> pacientes,int nivelAcceso) {
        lableUsuario.setText(usuario);
        lableEmpleados.setText(Empleados);
        this.funcionarios = funcionarios;
        this.pacientes = pacientes;
        this.nivelAcceso = nivelAcceso;
    }
}

package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.RRHH.PersonalAdmin;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.RRHH.PersonalNoMedicoInterno;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class SesionOtroFuncionarioController implements Initializable {
    @FXML
    private Label lableUsuario;

    @FXML
    private Label lableRut;

    @FXML
    private Label lableVacaciones;

    @FXML
    private Label lableSueldoBruto;

    @FXML
    private Label lableRol;

    @FXML
    private Button buttonCambioContrasena;

    @FXML
    private Button buttonCerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCerrarSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Clinica.cambioEscenaPaciente(event, "Login.fxml", "Inicio de Sesion!", null,null,null);
            }
        });

        buttonCambioContrasena.setOnAction(new EventHandler<javafx.event.ActionEvent>(){
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try {
                    Clinica.cambiarContrasenaEmpleado(event,lableUsuario.getText());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setInfo(Funcionario funcionario) {
        lableUsuario.setText(funcionario.getNombre());
        lableRut.setText(funcionario.getIDFuncionario());
        lableSueldoBruto.setText(String.valueOf(funcionario.getSueldoBruto()));
        lableRol.setText(funcionario.getRol());
        if (funcionario.getClass() == PersonalMedico.class){
            lableVacaciones.setText(String.valueOf(((PersonalMedico) funcionario).getVacaciones()));
        } else if (funcionario.getClass() == PersonalAdmin.class){
            lableVacaciones.setText(String.valueOf(((PersonalAdmin) funcionario).getVacaciones()));
        } else if (funcionario.getClass() == PersonalNoMedicoInterno.class) {
            lableVacaciones.setText(String.valueOf(((PersonalNoMedicoInterno) funcionario).getVacaciones()));
        }
    }
}
